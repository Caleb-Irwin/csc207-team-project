package interface_adapter.create_flashcard;

import use_case.create_flashcard.*;

/**
 * Controller for handling create flashcard requests.
 */

public class CreateFlashcardController {
    private final CreateFlashcardInputBoundary interactor;

    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void create(String question, String answer, String setName) {
        CreateFlashcardInputData inputData = new CreateFlashcardInputData(question, answer, setName);
        interactor.execute(inputData);
    }
}
