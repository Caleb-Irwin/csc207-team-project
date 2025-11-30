package use_case.review_flashcards;

import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.FlashCardSetsDataAccessInterface;

/**
 * The Review Flashcards Interactor.
 */
public class ReviewFlashCardsInteractor implements ReviewFlashCardsInputBoundary {
    private final ReviewFlashCardsOutputBoundary reviewFlashCardsPresenter;
    private final FlashCardSetsDataAccessInterface flashCardSetsDAO;
    private final ViewManagerModel viewManagerModel;

    public ReviewFlashCardsInteractor(ReviewFlashCardsOutputBoundary reviewFlashCardsOutputBoundary,
            FlashCardSetsDataAccessInterface flashCardSetsDAO, ViewManagerModel viewManagerModel) {
        this.reviewFlashCardsPresenter = reviewFlashCardsOutputBoundary;
        this.flashCardSetsDAO = flashCardSetsDAO;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void execute(ReviewFlashCardsInputData reviewFlashCardsInputData) {
        final ReviewFlashCardsActionName actionName = reviewFlashCardsInputData.getActionName();
        final FlashCardSet flashCardSet = flashCardSetsDAO.getFlashCardSetById(
                viewManagerModel.getCurrentFlashCardSetId());
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
                currentCardIndex,
                showingQuestion));
    }
}
