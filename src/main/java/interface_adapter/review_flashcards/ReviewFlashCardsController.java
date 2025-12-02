package interface_adapter.review_flashcards;

import use_case.review_flashcards.ReviewFlashCardsInputBoundary;

public class ReviewFlashCardsController {
    private final ReviewFlashCardsInputBoundary userReviewFlashCardsUseCaseInteractor;

    public ReviewFlashCardsController(ReviewFlashCardsInputBoundary userReviewFlashCardsUseCaseInteractor) {
        this.userReviewFlashCardsUseCaseInteractor = userReviewFlashCardsUseCaseInteractor;
    }

    public void nextQuestion() {
        userReviewFlashCardsUseCaseInteractor.nextQuestion();
    }

    public void previousQuestion() {
        userReviewFlashCardsUseCaseInteractor.previousQuestion();
    }

    public void flipCard() {
        userReviewFlashCardsUseCaseInteractor.flipCard();
    }

    public void editSet() {
        userReviewFlashCardsUseCaseInteractor.editSet();
    }

    public void ensureCorrectSet(int flashCardSetId) {
        userReviewFlashCardsUseCaseInteractor.ensureCorrectSet(flashCardSetId);
    }
}
