package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * The Review Flashcards Interactor.
 */
public class ReviewFlashCardsInteractor implements ReviewFlashCardsInputBoundary {
    private final ReviewFlashCardsUserDataAccessInterface userDataAccessObject;
    private final ReviewFlashCardsOutputBoundary reviewFlashCardsPresenter;

    public ReviewFlashCardsInteractor(ReviewFlashCardsUserDataAccessInterface userDataAccessInterface,
            ReviewFlashCardsOutputBoundary reviewFlashCardsOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.reviewFlashCardsPresenter = reviewFlashCardsOutputBoundary;
    }

    @Override
    public void execute(ReviewFlashCardsInputData reviewFlashCardsInputData) {
        final ReviewFlashCardsActionName actionName = reviewFlashCardsInputData.getActionName();

    }
}
