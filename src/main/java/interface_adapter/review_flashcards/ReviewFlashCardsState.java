package interface_adapter.review_flashcards;

import entity.FlashCardSet;

/**
 * The state for the Review Flashcards View Model.
 */
public class ReviewFlashCardsState {
    private int currentCardIndex = 0;
    private boolean showingQuestion = true;
    private int flashCardSetId = -1;
    private FlashCardSet flashCardSet;

    public int getFlashCardSetId() {
        return flashCardSetId;
    }

    public void setFlashCardSetId(int flashCardSetId) {
        this.flashCardSetId = flashCardSetId;
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public FlashCardSet getFlashCardSet() {
        return flashCardSet;
    }

    public void setFlashCardSet(FlashCardSet flashCardSet) {
        this.flashCardSet = flashCardSet;
    }

    public void setCurrentCardIndex(int currentCardIndex) {
        this.currentCardIndex = currentCardIndex;
    }

    public boolean isShowingQuestion() {
        return showingQuestion;
    }

    public void setShowingQuestion(boolean showingQuestion) {
        this.showingQuestion = showingQuestion;
    }

}
