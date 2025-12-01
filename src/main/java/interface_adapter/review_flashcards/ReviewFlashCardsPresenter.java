package interface_adapter.review_flashcards;

import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.review_flashcards.ReviewFlashCardsOutputBoundary;

public class ReviewFlashCardsPresenter implements ReviewFlashCardsOutputBoundary {

    private final ReviewFlashCardsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public ReviewFlashCardsPresenter(ReviewFlashCardsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

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

    @Override
    public void flipCard() {
        ReviewFlashCardsState state = viewModel.getState();
        boolean showingQuestion = state.isShowingQuestion();
        state.setShowingQuestion(!showingQuestion);
        viewModel.firePropertyChange();
    }

    @Override
    public void editSet() {
        viewManagerModel.setState("create flashcard");
        viewManagerModel.firePropertyChange();
    }

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
