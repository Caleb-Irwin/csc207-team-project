package interface_adapter.create_flashcard;

import use_case.create_flashcard.*;

/**
 * Presenter for the Create Flashcard use case.
 */
public class CreateFlashcardPresenter implements CreateFlashcardOutputBoundary {

    @Override
    public void present(CreateFlashcardOutputData outputData) {
        if (outputData.isSuccess()) {
            System.out.println(outputData.getMessage());
        } else {
            System.out.println("Failed to create flashcard.");
        }
    }
}
