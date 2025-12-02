package interface_adapter.create_flashcard;

import entity.FlashCardSet;
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

        CreateFlashcardState state = setCurrentSet(outputData.getSet());
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);
        viewManagerModel.firePropertyChange();

        if (outputData.isRedirectHome()) {
            viewManagerModel.setState("generator");
            viewManagerModel.firePropertyChange();
        }
    }

    private CreateFlashcardState setCurrentSet(FlashCardSet set) {
        CreateFlashcardState state = new CreateFlashcardState();
        state.setSetName(set.getSetName());
        state.setQuestions(set.getFlashcards().stream().map(card -> card.getQuestion()).toList());
        state.setAnswers(set.getFlashcards().stream().map(card -> card.getAnswer()).toList());
        return state;
    }
}