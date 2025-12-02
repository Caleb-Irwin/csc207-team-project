package interface_adapter.create_flashcard;

import use_case.create_flashcard.CreateFlashcardInputBoundary;
import use_case.create_flashcard.CreateFlashcardInputData;

import java.util.List;

/**
 * Controller for handling the Create Flashcard use case.
 */
public class CreateFlashcardController {

    private final CreateFlashcardInputBoundary interactor;

    /**
     * Constructs a CreateFlashcardController with the provided interactor.
     *
     * @param interactor the interactor that handles the business logic for the create flashcard use case
     */
    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Saves flashcards by passing the data to the interactor for processing.
     *
     * @param setName the name of the flashcard set
     * @param questions a list of questions to be saved
     * @param answers a list of answers corresponding to the questions
     */
    public void saveFlashcards(
            String setName,
            List<String> questions,
            List<String> answers) {
        CreateFlashcardInputData data = new CreateFlashcardInputData(setName, questions, answers);
        interactor.saveFlashcards(data);
    }

    /**
     * Deletes the current flashcard set through the interactor.
     * This method is used to delete a flashcard set based on the current context.
     */
    public void deleteSet() {
        // Call interactor to delete the flashcard set
        interactor.deleteSet();
    }

    /**
     * Ensures that the correct flashcard set is selected by the interactor.
     * This method checks if the current flashcard set exists and is valid.
     */
    public void ensureCorrectSet() {
        // Ensure the correct set is selected by the interactor
        interactor.ensureCorrectSet();
    }
}