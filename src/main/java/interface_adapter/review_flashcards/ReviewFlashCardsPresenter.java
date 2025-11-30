package interface_adapter.review_flashcards;

import use_case.review_flashcards.ReviewFlashCardsOutputBoundary;

public class ReviewFlashCardsPresenter implements ReviewFlashCardsOutputBoundary {

    private final ReviewFlashCardsViewModel viewModel;

    public ReviewFlashCardsPresenter(ReviewFlashCardsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(use_case.review_flashcards.ReviewFlashCardsOutputData outputData) {
        ReviewFlashCardsState state = viewModel.getState();
        state.setCurrentCardIndex(outputData.getCurrentCardIndex());
        state.setShowingQuestion(outputData.isShowingQuestion());
        viewModel.firePropertyChange();
    }
}
