package interface_adapter.review_flashcards;

import use_case.review_flashcards.ReviewFlashCardsInputBoundary;

/**
 * Controller for the Review Flashcards feature.
 * Handles user interactions from the view and delegates to the use case
 * interactor.
 */
public class ReviewFlashCardsController {
    private final ReviewFlashCardsInputBoundary userReviewFlashCardsUseCaseInteractor;

    /**
     * Constructs a ReviewFlashCardsController with the specified interactor.
     *
     * @param userReviewFlashCardsUseCaseInteractor the use case interactor for
     *                                              reviewing flashcards
     */
    public ReviewFlashCardsController(ReviewFlashCardsInputBoundary userReviewFlashCardsUseCaseInteractor) {
        this.userReviewFlashCardsUseCaseInteractor = userReviewFlashCardsUseCaseInteractor;
    }

    /**
     * Handles the user action to move to the next flashcard.
     */
    public void nextQuestion() {
        userReviewFlashCardsUseCaseInteractor.nextQuestion();
    }

    /**
     * Handles the user action to move to the previous flashcard.
     */
    public void previousQuestion() {
        userReviewFlashCardsUseCaseInteractor.previousQuestion();
    }

    /**
     * Handles the user action to flip the current flashcard.
     */
    public void flipCard() {
        userReviewFlashCardsUseCaseInteractor.flipCard();
    }

    /**
     * Handles the user action to edit the current flashcard set.
     */
    public void editSet() {
        userReviewFlashCardsUseCaseInteractor.editSet();
    }

    /**
     * Ensures that the view is displaying the correct flashcard set.
     *
     * @param flashCardSetId the ID of the flashcard set to verify
     */
    public void ensureCorrectSet(int flashCardSetId) {
        userReviewFlashCardsUseCaseInteractor.ensureCorrectSet(flashCardSetId);
    }
}
