package use_case.review_flashcards;

import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.FlashCardSetsDataAccessInterface;

/**
 * The Review Flashcards Interactor.
 */
public class ReviewFlashCardsInteractor implements ReviewFlashCardsInputBoundary {
    private final ReviewFlashCardsOutputBoundary presenter;
    private final FlashCardSetsDataAccessInterface flashCardSetsDAO;
    private final ViewManagerModel viewManagerModel;

    public ReviewFlashCardsInteractor(
            ReviewFlashCardsOutputBoundary reviewFlashCardsOutputBoundary,
            FlashCardSetsDataAccessInterface flashCardSetsDAO, ViewManagerModel viewManagerModel) {
        this.presenter = reviewFlashCardsOutputBoundary;
        this.flashCardSetsDAO = flashCardSetsDAO;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void nextQuestion() {
        presenter.nextCard();
    }

    @Override
    public void previousQuestion() {
        presenter.previousCard();
    }

    @Override
    public void flipCard() {
        presenter.flipCard();
    }

    @Override
    public void editSet() {
        viewManagerModel.setState("create flashcard");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void ensureCorrectSet(int flashCardSetId) {
        FlashCardSet currentSet = getCurrentFlashCardSet();
        if (currentSet != null && currentSet.getId() == flashCardSetId) {
            return;
        } else {
            presenter.setFlashCardSet(currentSet);
        }
    }

    private FlashCardSet getCurrentFlashCardSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        return flashCardSetsDAO.getFlashCardSetById(currentFlashCardSetId);
    }
}
