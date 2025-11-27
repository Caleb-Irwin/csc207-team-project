package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * The Input Data for the Review Flashcards Use Case.
 */
public class ReviewFlashCardsInputData {

    private final ReviewFlashCardsActionName actionName;
    private final int currentCardIndex;
    private final boolean showingQuestion;
    private final FlashCardSet flashCardSet;

    public ReviewFlashCardsInputData(ReviewFlashCardsActionName actionName, int currentCardIndex,
            boolean showingQuestion, FlashCardSet flashCardSet) {
        this.actionName = actionName;
        this.currentCardIndex = currentCardIndex;
        this.showingQuestion = showingQuestion;
        this.flashCardSet = flashCardSet;
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

    public FlashCardSet getFlashCardSet() {
        return flashCardSet;
    }

}
