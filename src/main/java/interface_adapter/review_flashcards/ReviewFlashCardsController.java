package interface_adapter.review_flashcards;

import use_case.review_flashcards.ReviewFlashCardsInputBoundary;
import use_case.review_flashcards.ReviewFlashCardsInputData;

public class ReviewFlashCardsController {
    private final ReviewFlashCardsInputBoundary userReviewFlashCardsUseCaseInteractor;

    public ReviewFlashCardsController(ReviewFlashCardsInputBoundary userReviewFlashCardsUseCaseInteractor) {
        this.userReviewFlashCardsUseCaseInteractor = userReviewFlashCardsUseCaseInteractor;
    }

    /**
     * Executes the Review Flashcards Use Case.
     * 
     * @param actionName the action to perform
     */
    public void execute(ReviewFlashCardsInputData inputData) {
        userReviewFlashCardsUseCaseInteractor.execute(inputData);
    }
}
