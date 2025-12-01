package interface_adapter.create_flashcard;
import interface_adapter.ViewManagerModel;
import use_case.create_flashcard.CreateFlashcardOutputBoundary;
import use_case.create_flashcard.CreateFlashcardOutputData;

public class CreateFlashcardPresenter implements CreateFlashcardOutputBoundary {

    private final CreateFlashcardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateFlashcardPresenter(CreateFlashcardViewModel viewModel, ViewManagerModel viewManagerModel) {

        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(CreateFlashcardOutputData outputData) {

        CreateFlashcardState state = new CreateFlashcardState();
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);
//        if (outputData.isSuccess() && outputData.getSetName() != null) {
//            viewManagerModel.setCurrentFlashCardSetId(outputData.getSetId());
//            viewManagerModel.setState("review flashcards");
//            viewManagerModel.firePropertyChange();
//        }

    }
}