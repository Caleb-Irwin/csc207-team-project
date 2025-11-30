package use_case.create_flashcard;

import entity.FlashCard;
import entity.FlashCardSet;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final CreateFlashcardDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(CreateFlashcardDataAccessInterface dataAccess,
            CreateFlashcardOutputBoundary presenter) {
        assert dataAccess != null;
        assert presenter != null;

        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Save an ENTIRE flashcard set (UI Save button).
     */
    @Override
    public void save(CreateFlashcardInputData inputData) {

        String setName = inputData.getSetName();
        List<String> questions = inputData.getQuestions();
        List<String> answers = inputData.getAnswers();

        FlashCardSet set = new FlashCardSet(setName, new ArrayList<>(), 0);

        for (int i = 0; i < questions.size(); i++) {
            String q = questions.get(i);
            String a = (i < answers.size()) ? answers.get(i) : "";

            if ((q == null || q.isBlank()) && (a == null || a.isBlank())) {
                continue;
            }

            set.addFlashcard(new FlashCard(q, a));
        }

        dataAccess.saveSet(set);

        CreateFlashcardOutputData output = new CreateFlashcardOutputData(setName, true, "Set saved!");
        presenter.present(output);
    }

    /**
     * Delete an entire flashcard set.
     */
    @Override
    public void delete(String setName) {

        boolean existed = dataAccess.existsByName(setName);
        dataAccess.deleteSet(setName);

        String msg = existed ? "Set deleted!" : "Set does not exist.";

        CreateFlashcardOutputData output = new CreateFlashcardOutputData(setName, true, msg);
        presenter.present(output);
    }
}