package use_case.review_flashcards;

import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.FlashCardSetsDataAccessInterface;

/**
 * The Review Flashcards Interactor.
 * Handles the business logic for reviewing flashcards, including navigation
 * between cards and transitioning to edit mode.
 */
public class ReviewFlashCardsInteractor implements ReviewFlashCardsInputBoundary {
    private final ReviewFlashCardsOutputBoundary presenter;
    private final FlashCardSetsDataAccessInterface flashCardSetsDAO;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a ReviewFlashCardsInteractor with the required dependencies.
     *
     * @param reviewFlashCardsOutputBoundary the presenter for output
     * @param flashCardSetsDAO               the data access object for flashcard
     *                                       sets
     * @param viewManagerModel               the view manager model for navigation
     */
    public ReviewFlashCardsInteractor(
            ReviewFlashCardsOutputBoundary reviewFlashCardsOutputBoundary,
            FlashCardSetsDataAccessInterface flashCardSetsDAO, ViewManagerModel viewManagerModel) {
        this.presenter = reviewFlashCardsOutputBoundary;
        this.flashCardSetsDAO = flashCardSetsDAO;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextQuestion() {
        presenter.nextCard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void previousQuestion() {
        presenter.previousCard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flipCard() {
        presenter.flipCard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editSet() {
        viewManagerModel.setState("create flashcard");
        viewManagerModel.firePropertyChange();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ensureCorrectSet(int flashCardSetId) {
        FlashCardSet currentSet = getCurrentFlashCardSet();
        if (currentSet != null && currentSet.getId() == flashCardSetId) {
            return;
        } else {
            presenter.setFlashCardSet(currentSet);
        }
    }

    /**
     * Retrieves the currently selected flashcard set from the data source.
     *
     * @return the current FlashCardSet, or null if not found
     */
    private FlashCardSet getCurrentFlashCardSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        return flashCardSetsDAO.getFlashCardSetById(currentFlashCardSetId);
    }
}
