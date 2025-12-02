package interface_adapter.review_flashcards;

import entity.FlashCardSet;

/**
 * The state for the Review Flashcards View Model.
 * Contains all the data needed to render the flashcard review view.
 */
public class ReviewFlashCardsState {
    private int currentCardIndex = 0;
    private boolean showingQuestion = true;
    private int flashCardSetId = -1;
    private FlashCardSet flashCardSet;

    /**
     * Returns the ID of the current flashcard set.
     *
     * @return the flashcard set ID, or -1 if no set is selected
     */
    public int getFlashCardSetId() {
        return flashCardSetId;
    }

    /**
     * Sets the flashcard set ID.
     *
     * @param flashCardSetId the ID to set
     */
    public void setFlashCardSetId(int flashCardSetId) {
        this.flashCardSetId = flashCardSetId;
    }

    /**
     * Returns the index of the currently displayed flashcard.
     *
     * @return the current card index (0-based)
     */
    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    /**
     * Returns the current flashcard set.
     *
     * @return the FlashCardSet being reviewed
     */
    public FlashCardSet getFlashCardSet() {
        return flashCardSet;
    }

    /**
     * Sets the flashcard set to review.
     *
     * @param flashCardSet the FlashCardSet to set
     */
    public void setFlashCardSet(FlashCardSet flashCardSet) {
        this.flashCardSet = flashCardSet;
    }

    /**
     * Sets the index of the currently displayed flashcard.
     *
     * @param currentCardIndex the index to set (0-based)
     */
    public void setCurrentCardIndex(int currentCardIndex) {
        this.currentCardIndex = currentCardIndex;
    }

    /**
     * Returns whether the question side of the card is currently showing.
     *
     * @return true if showing the question, false if showing the answer
     */
    public boolean isShowingQuestion() {
        return showingQuestion;
    }

    /**
     * Sets whether the question side of the card should be displayed.
     *
     * @param showingQuestion true to show the question, false to show the answer
     */
    public void setShowingQuestion(boolean showingQuestion) {
        this.showingQuestion = showingQuestion;
    }

}
