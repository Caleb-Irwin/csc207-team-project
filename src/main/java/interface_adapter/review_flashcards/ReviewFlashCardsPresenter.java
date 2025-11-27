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
        state.setFlashCardSet(outputData.getFlashcardSet());
        state.setCurrentCardIndex(0);
        state.setShowingQuestion(true);
        viewModel.setState(state);
    }
}
