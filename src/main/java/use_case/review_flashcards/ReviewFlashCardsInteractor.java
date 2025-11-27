package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * The Review Flashcards Interactor.
 */
public class ReviewFlashCardsInteractor implements ReviewFlashCardsInputBoundary {
    private final ReviewFlashCardsOutputBoundary reviewFlashCardsPresenter;

    public ReviewFlashCardsInteractor(ReviewFlashCardsOutputBoundary reviewFlashCardsOutputBoundary) {
        this.reviewFlashCardsPresenter = reviewFlashCardsOutputBoundary;
    }

    @Override
    public void execute(ReviewFlashCardsInputData reviewFlashCardsInputData) {
        final ReviewFlashCardsActionName actionName = reviewFlashCardsInputData.getActionName();
        final FlashCardSet flashCardSet = reviewFlashCardsInputData.getFlashCardSet();
        int currentCardIndex = reviewFlashCardsInputData.getCurrentCardIndex();
        boolean showingQuestion = reviewFlashCardsInputData.isShowingQuestion();

        switch (actionName) {
            case NEXT_CARD:
                if (currentCardIndex < flashCardSet.getFlashcards().size() - 1) {
                    currentCardIndex++;
                } else {
                    currentCardIndex = 0;
                }
                showingQuestion = true;
                break;
            case PREVIOUS_CARD:
                if (currentCardIndex > 0) {
                    currentCardIndex--;
                } else {
                    currentCardIndex = flashCardSet.getFlashcards().size() - 1;
                }
                showingQuestion = true;
                break;
            case FLIP_CARD:
                showingQuestion = !showingQuestion;
                break;
        }

        reviewFlashCardsPresenter.prepareSuccessView(new ReviewFlashCardsOutputData(
                flashCardSet,
                currentCardIndex,
                showingQuestion));
    }
}
