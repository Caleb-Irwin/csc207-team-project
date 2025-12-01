package use_case.create_flashcard;

import entity.FlashCard;
import entity.FlashCardSet;
import use_case.FlashCardSetsDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final FlashCardSetsDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(FlashCardSetsDataAccessInterface dataAccess,
                                     CreateFlashcardOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void saveFlashcards(CreateFlashcardInputData inputData) {

        Integer id = inputData.getSetId();
        String setName = inputData.getSetName();

        FlashCardSet existing = dataAccess.getFlashCardSetById(id);

        FlashCardSet set;
        if (existing != null) {
            // update existing
            set = existing;
            set.setSetName(setName);
            set.getFlashcards().clear();
        } else {
            // create new
            set = new FlashCardSet(setName, new ArrayList<>(), id);
        }

        List<String> questions = inputData.getQuestions();
        List<String> answers = inputData.getAnswers();

        for (int i = 0; i < questions.size(); i++) {
            set.addFlashcard(new FlashCard(questions.get(i), answers.get(i)));
        }

        if (existing == null) {
            dataAccess.createFlashCardSet(set);
        } else {
            dataAccess.updateFlashCardSet(id, set);
        }

        presenter.present(new CreateFlashcardOutputData(
                id,
                setName,
                true,
                "Set saved!"
        ));
    }

    @Override
    public void deleteSet(Integer setId) {

        boolean existed = dataAccess.getFlashCardSetById(setId) != null;

        if (existed) {
            dataAccess.deleteFlashCardSet(setId);
        }

        presenter.present(new CreateFlashcardOutputData(
                setId,
                null,
                true,
                existed ? "Set deleted!" : "Set does not exist."
        ));
    }
}