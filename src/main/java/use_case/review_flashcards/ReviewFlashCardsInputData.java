package use_case.review_flashcards;

/**
 * The Input Data for the Review Flashcards Use Case.
 */
public class ReviewFlashCardsInputData {

    private final ReviewFlashCardsActionName actionName;
    private final int currentCardIndex;
    private final boolean showingQuestion;

    public ReviewFlashCardsInputData(ReviewFlashCardsActionName actionName, int currentCardIndex,
            boolean showingQuestion) {
        this.actionName = actionName;
        this.currentCardIndex = currentCardIndex;
        this.showingQuestion = showingQuestion;
    }

    public ReviewFlashCardsActionName getActionName() {
        return actionName;
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public boolean isShowingQuestion() {
        return showingQuestion;
    }
}
