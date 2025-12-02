package use_case;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.review_flashcards.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ReviewFlashCardsInteractor
 */
class ReviewFlashCardsInteractorTest {

    /**
     * Fake Presenter — captures the calls made by interactor
     */
    private static class FakePresenter implements ReviewFlashCardsOutputBoundary {
        boolean nextCardCalled = false;
        boolean previousCardCalled = false;
        boolean flipCardCalled = false;
        boolean editSetCalled = false;
        FlashCardSet receivedFlashCardSet = null;
        boolean setFlashCardSetCalled = false;

        @Override
        public void nextCard() {
            nextCardCalled = true;
        }

        @Override
        public void previousCard() {
            previousCardCalled = true;
        }

        @Override
        public void flipCard() {
            flipCardCalled = true;
        }

        @Override
        public void editSet() {
            editSetCalled = true;
        }

        @Override
        public void setFlashCardSet(FlashCardSet flashCardSet) {
            setFlashCardSetCalled = true;
            receivedFlashCardSet = flashCardSet;
        }
    }

    private FakeFlashCardDataAccess dataAccess;
    private FakePresenter presenter;
    private ViewManagerModel viewManagerModel;

    @BeforeEach
    void setUp() {
        dataAccess = new FakeFlashCardDataAccess();
        presenter = new FakePresenter();
        viewManagerModel = new ViewManagerModel();
    }

    // ===================== nextQuestion() tests =====================

    @Test
    void testNextQuestion() {
        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.nextQuestion();

        assertTrue(presenter.nextCardCalled);
        assertFalse(presenter.previousCardCalled);
        assertFalse(presenter.flipCardCalled);
    }

    // ===================== previousQuestion() tests =====================

    @Test
    void testPreviousQuestion() {
        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.previousQuestion();

        assertTrue(presenter.previousCardCalled);
        assertFalse(presenter.nextCardCalled);
        assertFalse(presenter.flipCardCalled);
    }

    // ===================== flipCard() tests =====================

    @Test
    void testFlipCard() {
        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.flipCard();

        assertTrue(presenter.flipCardCalled);
        assertFalse(presenter.nextCardCalled);
        assertFalse(presenter.previousCardCalled);
    }

    // ===================== editSet() tests =====================

    @Test
    void testEditSet() {
        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.editSet();

        assertEquals("create flashcard", viewManagerModel.getState());
    }

    @Test
    void testEditSetChangesViewState() {
        viewManagerModel.setState("review flashcards");

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.editSet();

        assertEquals("create flashcard", viewManagerModel.getState());
    }

    // ===================== ensureCorrectSet() tests =====================

    @Test
    void testEnsureCorrectSetWhenSetMatchesId() {
        // Setup: current set has id 5, and we're asking for id 5
        FlashCardSet existingSet = new FlashCardSet("Test Set", new ArrayList<>(), 5);
        dataAccess.storedSet = existingSet;
        viewManagerModel.setCurrentFlashCardSetId(5);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.ensureCorrectSet(5);

        // Should not call setFlashCardSet since the set already matches
        assertFalse(presenter.setFlashCardSetCalled);
        assertNull(presenter.receivedFlashCardSet);
    }

    @Test
    void testEnsureCorrectSetWhenSetDoesNotMatchId() {
        // Setup: current set has id 5, but we're asking for id 10
        FlashCardSet existingSet = new FlashCardSet("Test Set", new ArrayList<>(), 5);
        dataAccess.storedSet = existingSet;
        viewManagerModel.setCurrentFlashCardSetId(5);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.ensureCorrectSet(10);

        // Should call setFlashCardSet with the current set
        assertTrue(presenter.setFlashCardSetCalled);
        assertEquals(existingSet, presenter.receivedFlashCardSet);
    }

    @Test
    void testEnsureCorrectSetWhenCurrentSetIsNull() {
        // Setup: no set exists in data access
        viewManagerModel.setCurrentFlashCardSetId(99);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.ensureCorrectSet(5);

        // Should call setFlashCardSet with null
        assertTrue(presenter.setFlashCardSetCalled);
        assertNull(presenter.receivedFlashCardSet);
    }

    @Test
    void testEnsureCorrectSetWhenCurrentSetIsNullAndIdMatches() {
        // Setup: no set exists, asking for id 99 (same as current)
        viewManagerModel.setCurrentFlashCardSetId(99);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        interactor.ensureCorrectSet(99);

        // currentSet is null, so currentSet.getId() would throw NPE
        // The code path: currentSet != null is false, so it goes to else branch
        assertTrue(presenter.setFlashCardSetCalled);
        assertNull(presenter.receivedFlashCardSet);
    }

    @Test
    void testEnsureCorrectSetWithFlashcards() {
        // Setup: set with flashcards
        List<FlashCard> cards = new ArrayList<>();
        cards.add(new FlashCard("Q1", "A1"));
        cards.add(new FlashCard("Q2", "A2"));
        FlashCardSet existingSet = new FlashCardSet("Study Set", cards, 7);
        dataAccess.storedSet = existingSet;
        viewManagerModel.setCurrentFlashCardSetId(7);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        // Asking for different id to trigger setFlashCardSet
        interactor.ensureCorrectSet(3);

        assertTrue(presenter.setFlashCardSetCalled);
        assertNotNull(presenter.receivedFlashCardSet);
        assertEquals(2, presenter.receivedFlashCardSet.getFlashcards().size());
        assertEquals("Study Set", presenter.receivedFlashCardSet.getSetName());
    }

    // ===================== Integration tests =====================

    @Test
    void testMultipleOperationsInSequence() {
        FlashCardSet existingSet = new FlashCardSet("Test Set", new ArrayList<>(), 1);
        dataAccess.storedSet = existingSet;
        viewManagerModel.setCurrentFlashCardSetId(1);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        // Perform multiple operations
        interactor.nextQuestion();
        assertTrue(presenter.nextCardCalled);

        interactor.flipCard();
        assertTrue(presenter.flipCardCalled);

        interactor.previousQuestion();
        assertTrue(presenter.previousCardCalled);

        // All operations should have been called
        assertTrue(presenter.nextCardCalled);
        assertTrue(presenter.flipCardCalled);
        assertTrue(presenter.previousCardCalled);
    }

    @Test
    void testEnsureCorrectSetThenEdit() {
        FlashCardSet existingSet = new FlashCardSet("Test Set", new ArrayList<>(), 5);
        dataAccess.storedSet = existingSet;
        viewManagerModel.setCurrentFlashCardSetId(5);

        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        // First ensure correct set (should not trigger presenter since IDs match)
        interactor.ensureCorrectSet(5);
        assertFalse(presenter.setFlashCardSetCalled);

        // Then edit the set
        interactor.editSet();
        assertEquals("create flashcard", viewManagerModel.getState());
    }

    @Test
    void testConstructorInitialization() {
        ReviewFlashCardsInteractor interactor = new ReviewFlashCardsInteractor(presenter, dataAccess, viewManagerModel);

        // Just verify the interactor was created successfully
        assertNotNull(interactor);
    }
}
