package use_case.review_flashcards;

/**
 * Output Data for the Review Flashcards Use Case.
 */
public class ReviewFlashCardsOutputData {

    private final int currentCardIndex;
    private final boolean showingQuestion;

    public ReviewFlashCardsOutputData(int currentCardIndex, boolean showingQuestion) {
        this.currentCardIndex = currentCardIndex;
        this.showingQuestion = showingQuestion;
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public boolean isShowingQuestion() {
        return showingQuestion;
    }
}
