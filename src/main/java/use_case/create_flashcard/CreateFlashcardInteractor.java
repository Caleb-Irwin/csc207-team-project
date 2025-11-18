package use_case.create_flashcard;

import entity.FlashCardSet;

import java.util.ArrayList;

import entity.FlashCard;

/**
 * Interactor for the Create Flashcard use case.
 */
public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final CreateFlashcardDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;

    public CreateFlashcardInteractor(CreateFlashcardDataAccessInterface dataAccess,
            CreateFlashcardOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateFlashcardInputData inputData) {
        String setName = inputData.getSetName();
        String question = inputData.getQuestions();
        String answer = inputData.getAnswers();

        FlashCardSet set = dataAccess.load(setName);
        if (set == null) {
            set = new FlashCardSet(setName, new ArrayList<>());
        }

        FlashCard flashcard = new FlashCard(question, answer);
        set.addFlashcard(flashcard);

        dataAccess.saveSet(set);

        // Output data
        CreateFlashcardOutputData output = new CreateFlashcardOutputData(
                setName, question, answer, true, "Flashcard created successfully!");

        presenter.present(output);
    }
}
