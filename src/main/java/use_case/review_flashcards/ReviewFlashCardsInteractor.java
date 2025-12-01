package use_case.review_flashcards;

import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import interface_adapter.review_flashcards.ReviewFlashCardsState;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;
import use_case.FlashCardSetsDataAccessInterface;

/**
 * The Review Flashcards Interactor.
 */
public class ReviewFlashCardsInteractor implements ReviewFlashCardsInputBoundary {
    private final ReviewFlashCardsViewModel viewModel;
    private final ReviewFlashCardsOutputBoundary reviewFlashCardsPresenter;
    private final FlashCardSetsDataAccessInterface flashCardSetsDAO;
    private final ViewManagerModel viewManagerModel;

    public ReviewFlashCardsInteractor(
            ReviewFlashCardsViewModel viewModel, ReviewFlashCardsOutputBoundary reviewFlashCardsOutputBoundary,
            FlashCardSetsDataAccessInterface flashCardSetsDAO, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.reviewFlashCardsPresenter = reviewFlashCardsOutputBoundary;
        this.flashCardSetsDAO = flashCardSetsDAO;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void nextQuestion() {
        final FlashCardSet flashCardSet = getCurrentFlashCardSet();
        int currentCardIndex = getState().getCurrentCardIndex();
        if (currentCardIndex < flashCardSet.getFlashcards().size() - 1) {
            currentCardIndex++;
        } else {
            currentCardIndex = 0;
        }
        reviewFlashCardsPresenter.prepareSuccessView(new ReviewFlashCardsOutputData(
                currentCardIndex,
                true));
    }

    @Override
    public void previousQuestion() {
        final FlashCardSet flashCardSet = getCurrentFlashCardSet();
        int currentCardIndex = getState().getCurrentCardIndex();
        if (currentCardIndex > 0) {
            currentCardIndex--;
        } else {
            currentCardIndex = flashCardSet.getFlashcards().size() - 1;
        }
        reviewFlashCardsPresenter.prepareSuccessView(new ReviewFlashCardsOutputData(
                currentCardIndex,
                true));
    }

    @Override
    public void flipCard() {
        boolean showingQuestion = getState().isShowingQuestion();
        reviewFlashCardsPresenter.prepareSuccessView(new ReviewFlashCardsOutputData(
                getState()
                        .getCurrentCardIndex(),
                !showingQuestion));
    }

    @Override
    public void editSet() {
        // Implementation for editing the flashcard set
    }

    private ReviewFlashCardsState getState() {
        return viewModel.getState();
    }

    private FlashCardSet getCurrentFlashCardSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        return flashCardSetsDAO.getFlashCardSetById(currentFlashCardSetId);
    }
}
