package use_case.review_flashcards;

enum ReviewFlashCardsActionName {
    NEXT_CARD,
    PREVIOUS_CARD,
    FLIP_CARD
}

/**
 * The Input Data for the Review Flashcards Use Case.
 */
public class ReviewFlashCardsInputData {

    private final ReviewFlashCardsActionName actionName;

    public ReviewFlashCardsInputData(ReviewFlashCardsActionName actionName) {
        this.actionName = actionName;
    }

    public ReviewFlashCardsActionName getActionName() {
        return actionName;
    }

}
