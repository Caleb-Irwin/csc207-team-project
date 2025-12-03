package interface_adapter.review_flashcards;

import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.review_flashcards.ReviewFlashCardsOutputBoundary;

/**
 * Presenter for the Review Flashcards feature.
 * Transforms use case output data and updates the view model for display.
 */
public class ReviewFlashCardsPresenter implements ReviewFlashCardsOutputBoundary {

    private final ReviewFlashCardsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a ReviewFlashCardsPresenter with the required view models.
     *
     * @param viewModel        the view model for the review flashcards view
     * @param viewManagerModel the view manager model for navigation
     */
    public ReviewFlashCardsPresenter(ReviewFlashCardsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * {@inheritDoc}
     * Advances to the next card, wrapping around to the first card if at the end.
     */
    @Override
    public void nextCard() {
        ReviewFlashCardsState state = viewModel.getState();
        int currentCardIndex = state.getCurrentCardIndex();
        FlashCardSet flashCardSet = state.getFlashCardSet();
        if (currentCardIndex < flashCardSet.getFlashcards().size() - 1) {
            currentCardIndex++;
        } else {
            currentCardIndex = 0;
        }
        state.setCurrentCardIndex(currentCardIndex);
        state.setShowingQuestion(true);
        viewModel.firePropertyChange();
    }

    /**
     * {@inheritDoc}
     * Goes back to the previous card, wrapping to the last card if at the
     * beginning.
     */
    @Override
    public void previousCard() {
        ReviewFlashCardsState state = viewModel.getState();
        int currentCardIndex = state.getCurrentCardIndex();
        if (currentCardIndex > 0) {
            currentCardIndex--;
        } else {
            currentCardIndex = state.getFlashCardSet().getFlashcards().size() - 1;
        }
        state.setCurrentCardIndex(currentCardIndex);
        state.setShowingQuestion(true);
        viewModel.firePropertyChange();
    }

    /**
     * {@inheritDoc}
     * Toggles between showing the question and the answer side of the card.
     */
    @Override
    public void flipCard() {
        ReviewFlashCardsState state = viewModel.getState();
        boolean showingQuestion = state.isShowingQuestion();
        state.setShowingQuestion(!showingQuestion);
        viewModel.firePropertyChange();
    }

    /**
     * {@inheritDoc}
     * Navigates to the create flashcard view for editing.
     */
    @Override
    public void editSet() {
        viewManagerModel.setState("create flashcard");
        viewManagerModel.firePropertyChange();
    }

    /**
     * {@inheritDoc}
     * Sets the flashcard set in the view model state and resets the card index.
     */
    @Override
    public void setFlashCardSet(FlashCardSet flashCardSet) {
        ReviewFlashCardsState state = viewModel.getState();
        if (flashCardSet == null) {
            state.setFlashCardSetId(-1);
        } else {
            state.setFlashCardSetId(flashCardSet.getId());

        }
        state.setFlashCardSet(flashCardSet);
        state.setCurrentCardIndex(0);
        state.setShowingQuestion(true);
    }
}
