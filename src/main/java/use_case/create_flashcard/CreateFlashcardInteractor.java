package use_case.create_flashcard;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.FlashCardSetsDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final FlashCardSetsDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;
    private final ViewManagerModel viewManagerModel;

    public CreateFlashcardInteractor(FlashCardSetsDataAccessInterface dataAccess,
            CreateFlashcardOutputBoundary presenter,
            ViewManagerModel viewManagerModel) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void saveFlashcards(CreateFlashcardInputData inputData) {
        String setName = inputData.getSetName();
        FlashCardSet set = getCurrentFlashCardSet();
        Integer id = set.getId();

        List<String> questions = inputData.getQuestions();
        List<String> answers = inputData.getAnswers();

        ArrayList<FlashCard> flashcards = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            flashcards.add(new FlashCard(questions.get(i), answers.get(i)));
        }
        set.setSetName(setName);
        set.setFlashcards(flashcards);

        dataAccess.updateFlashCardSet(id, set);

        presenter.present(new CreateFlashcardOutputData(
                id,
                setName,
                true,
                "Set saved!",
                false,
                set));
    }

    @Override
    public void deleteSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        boolean existed = dataAccess.getFlashCardSetById(currentFlashCardSetId) != null;

        if (existed) {
            dataAccess.deleteFlashCardSet(currentFlashCardSetId);
        }

        presenter.present(new CreateFlashcardOutputData(
                currentFlashCardSetId,
                null,
                true,
                existed ? "Set deleted!" : "Set does not exist.", existed, getCurrentFlashCardSet()));
    }

    @Override
    public void ensureCorrectSet() {
        FlashCardSet currentSet = getCurrentFlashCardSet();
        Integer id = currentSet != null ? currentSet.getId() : null;
        presenter.present(new CreateFlashcardOutputData(
                id,
                currentSet != null ? currentSet.getSetName() : "",
                false,
                "",
                false,
                currentSet));
    }

    private FlashCardSet getCurrentFlashCardSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        return dataAccess.getFlashCardSetById(currentFlashCardSetId);
    }
}