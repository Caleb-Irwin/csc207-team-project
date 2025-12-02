package interface_adapter.create_flashcard;

import use_case.create_flashcard.CreateFlashcardInputBoundary;
import use_case.create_flashcard.CreateFlashcardInputData;

import java.util.List;

/**
 * Controller for Create Flashcard use case.
 */
public class CreateFlashcardController {

    private final CreateFlashcardInputBoundary interactor;

    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void saveFlashcards(
            String setName,
            List<String> questions,
            List<String> answers) {

        CreateFlashcardInputData data = new CreateFlashcardInputData(setName, questions, answers);

        interactor.saveFlashcards(data);
    }

    public void deleteSet() {
        interactor.deleteSet();
    }

    public void ensureCorrectSet() {
        interactor.ensureCorrectSet();
    }
}