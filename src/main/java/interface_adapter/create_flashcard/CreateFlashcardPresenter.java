package interface_adapter.create_flashcard;

import use_case.create_flashcard.CreateFlashcardOutputBoundary;
import use_case.create_flashcard.CreateFlashcardOutputData;

public class CreateFlashcardPresenter implements CreateFlashcardOutputBoundary {

    private final CreateFlashcardViewModel viewModel;

    public CreateFlashcardPresenter(CreateFlashcardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(CreateFlashcardOutputData outputData) {

        CreateFlashcardState state = new CreateFlashcardState(viewModel.getState());
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);

    }
}