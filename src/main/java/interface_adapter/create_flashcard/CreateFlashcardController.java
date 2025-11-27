package interface_adapter.create_flashcard;

import use_case.create_flashcard.*;

import javax.swing.*;
import java.util.List;

/**
 * Controller for handling create flashcard requests.
 */

public class CreateFlashcardController {

    private final CreateFlashcardInputBoundary interactor;

    public CreateFlashcardController(CreateFlashcardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createFlashcard(String question, String answer, String setName) {
        CreateFlashcardInputData data = new CreateFlashcardInputData(question, answer, setName);
        interactor.execute(data);
    }

    public void saveFlashcards(String setName, List<String> questions, List<String> answers) {
        interactor.saveFlashcards(setName, questions, answers);
    }

    public void deleteSet(String setName) {
        interactor.deleteSet(setName);
    }
}