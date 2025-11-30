package interface_adapter.review_flashcards;

/**
 * The state for the Review Flashcards View Model.
 */
public class ReviewFlashCardsState {
    private int currentCardIndex = 0;
    private boolean showingQuestion = true;

    public int getCurrentCardIndex() {
        return currentCardIndex;
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
