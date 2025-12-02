package use_case;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.create_flashcard.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CreateFlashcardInteractor
 */
class CreateFlashcardInteractorTest {

    /**
     * Fake Presenter — captures the output passed by interactor
     */
    private static class FakePresenter implements CreateFlashcardOutputBoundary {
        CreateFlashcardOutputData received;

        @Override
        public void present(CreateFlashcardOutputData outputData) {
            received = outputData;
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

    // ===================== saveFlashcards() tests =====================

    @Test
    void testSaveFlashcardsUpdatesExistingSet() {
        // Setup existing set
        FlashCardSet existing = new FlashCardSet("Old Name", new ArrayList<>(), 5);
        existing.addFlashcard(new FlashCard("OldQ", "OldA"));
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(5);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        List<String> questions = List.of("NewQ1", "NewQ2");
        List<String> answers = List.of("NewA1", "NewA2");

        CreateFlashcardInputData input = new CreateFlashcardInputData("Updated Name", questions, answers);

        interactor.saveFlashcards(input);

        // Verify data access was updated
        assertNotNull(dataAccess.storedSet);
        assertEquals("Updated Name", dataAccess.storedSet.getSetName());
        assertEquals(2, dataAccess.storedSet.getFlashcards().size());
        assertEquals("NewQ1", dataAccess.storedSet.getFlashcards().get(0).getQuestion());
        assertEquals("NewA1", dataAccess.storedSet.getFlashcards().get(0).getAnswer());
        assertEquals("NewQ2", dataAccess.storedSet.getFlashcards().get(1).getQuestion());
        assertEquals("NewA2", dataAccess.storedSet.getFlashcards().get(1).getAnswer());
        assertEquals(5, dataAccess.updatedId);

        // Verify presenter output
        assertNotNull(presenter.received);
        assertEquals("Set saved!", presenter.received.getMessage());
        assertTrue(presenter.received.isSuccess());
        assertEquals(5, presenter.received.getSetId());
        assertEquals("Updated Name", presenter.received.getSetName());
        assertFalse(presenter.received.isRedirectHome());
        assertNotNull(presenter.received.getSet());
    }

    @Test
    void testSaveFlashcardsWithEmptyList() {
        FlashCardSet existing = new FlashCardSet("Test Set", new ArrayList<>(), 1);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(1);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        List<String> questions = List.of();
        List<String> answers = List.of();

        CreateFlashcardInputData input = new CreateFlashcardInputData("Empty Set", questions, answers);

        interactor.saveFlashcards(input);

        assertNotNull(dataAccess.storedSet);
        assertEquals("Empty Set", dataAccess.storedSet.getSetName());
        assertEquals(0, dataAccess.storedSet.getFlashcards().size());
        assertEquals("Set saved!", presenter.received.getMessage());
    }

    @Test
    void testSaveFlashcardsWithSingleCard() {
        FlashCardSet existing = new FlashCardSet("Single Card Set", new ArrayList<>(), 2);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(2);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        List<String> questions = List.of("Only Question");
        List<String> answers = List.of("Only Answer");

        CreateFlashcardInputData input = new CreateFlashcardInputData("Single Set", questions, answers);

        interactor.saveFlashcards(input);

        assertEquals(1, dataAccess.storedSet.getFlashcards().size());
        assertEquals("Only Question", dataAccess.storedSet.getFlashcards().get(0).getQuestion());
        assertEquals("Only Answer", dataAccess.storedSet.getFlashcards().get(0).getAnswer());
    }

    @Test
    void testSaveFlashcardsWithMultipleCards() {
        FlashCardSet existing = new FlashCardSet("Multi Set", new ArrayList<>(), 3);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(3);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        List<String> questions = List.of("Q1", "Q2", "Q3", "Q4", "Q5");
        List<String> answers = List.of("A1", "A2", "A3", "A4", "A5");

        CreateFlashcardInputData input = new CreateFlashcardInputData("Five Cards", questions, answers);

        interactor.saveFlashcards(input);

        assertEquals(5, dataAccess.storedSet.getFlashcards().size());
        for (int i = 0; i < 5; i++) {
            assertEquals("Q" + (i + 1), dataAccess.storedSet.getFlashcards().get(i).getQuestion());
            assertEquals("A" + (i + 1), dataAccess.storedSet.getFlashcards().get(i).getAnswer());
        }
    }

    // ===================== deleteSet() tests =====================

    @Test
    void testDeleteExistingSet() {
        FlashCardSet existing = new FlashCardSet("To Delete", new ArrayList<>(), 10);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(10);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.deleteSet();

        // Verify deletion
        assertNull(dataAccess.storedSet);
        assertEquals(10, dataAccess.deletedId);

        // Verify presenter output
        assertNotNull(presenter.received);
        assertEquals("Set deleted!", presenter.received.getMessage());
        assertTrue(presenter.received.isSuccess());
        assertEquals(10, presenter.received.getSetId());
        assertTrue(presenter.received.isRedirectHome());
    }

    @Test
    void testDeleteNonExistingSet() {
        viewManagerModel.setCurrentFlashCardSetId(99);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.deleteSet();

        // Verify no deletion occurred
        assertNull(dataAccess.deletedId);

        // Verify presenter output
        assertNotNull(presenter.received);
        assertEquals("Set does not exist.", presenter.received.getMessage());
        assertTrue(presenter.received.isSuccess());
        assertEquals(99, presenter.received.getSetId());
        assertFalse(presenter.received.isRedirectHome());
    }

    @Test
    void testDeleteSetWithDifferentId() {
        // Set exists with id 5, but we try to delete id 7
        FlashCardSet existing = new FlashCardSet("Wrong Set", new ArrayList<>(), 5);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(7);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.deleteSet();

        // Set should still exist because we tried to delete a different id
        assertNotNull(dataAccess.storedSet);
        assertEquals("Set does not exist.", presenter.received.getMessage());
        assertFalse(presenter.received.isRedirectHome());
    }

    // ===================== ensureCorrectSet() tests =====================

    @Test
    void testEnsureCorrectSetWithExistingSet() {
        FlashCardSet existing = new FlashCardSet("My Study Set", new ArrayList<>(), 15);
        existing.addFlashcard(new FlashCard("Q", "A"));
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(15);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.ensureCorrectSet();

        // Verify presenter output
        assertNotNull(presenter.received);
        assertEquals(15, presenter.received.getSetId());
        assertEquals("My Study Set", presenter.received.getSetName());
        assertFalse(presenter.received.isSuccess());
        assertEquals("", presenter.received.getMessage());
        assertFalse(presenter.received.isRedirectHome());
        assertNotNull(presenter.received.getSet());
        assertEquals("My Study Set", presenter.received.getSet().getSetName());
    }

    @Test
    void testEnsureCorrectSetWithNullSet() {
        // No set exists
        viewManagerModel.setCurrentFlashCardSetId(999);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.ensureCorrectSet();

        // Verify presenter output for null set
        assertNotNull(presenter.received);
        assertNull(presenter.received.getSetId());
        assertEquals("", presenter.received.getSetName());
        assertFalse(presenter.received.isSuccess());
        assertEquals("", presenter.received.getMessage());
        assertFalse(presenter.received.isRedirectHome());
        assertNull(presenter.received.getSet());
    }

    @Test
    void testEnsureCorrectSetWithEmptySetName() {
        FlashCardSet existing = new FlashCardSet("", new ArrayList<>(), 20);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(20);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.ensureCorrectSet();

        assertNotNull(presenter.received);
        assertEquals(20, presenter.received.getSetId());
        assertEquals("", presenter.received.getSetName());
        assertNotNull(presenter.received.getSet());
    }

    // ===================== Edge case tests =====================

    @Test
    void testSaveFlashcardsPreservesSetId() {
        FlashCardSet existing = new FlashCardSet("Original", new ArrayList<>(), 42);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(42);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        CreateFlashcardInputData input = new CreateFlashcardInputData("New Name", List.of("Q"), List.of("A"));

        interactor.saveFlashcards(input);

        // ID should be preserved
        assertEquals(42, dataAccess.storedSet.getId());
        assertEquals(42, presenter.received.getSetId());
    }

    @Test
    void testOutputDataFieldsAfterSave() {
        FlashCardSet existing = new FlashCardSet("Test", new ArrayList<>(), 7);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(7);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        CreateFlashcardInputData input = new CreateFlashcardInputData("Complete Test", List.of("Q1", "Q2"),
                List.of("A1", "A2"));

        interactor.saveFlashcards(input);

        // Verify all output data fields
        CreateFlashcardOutputData output = presenter.received;
        assertEquals(7, output.getSetId());
        assertEquals("Complete Test", output.getSetName());
        assertTrue(output.isSuccess());
        assertEquals("Set saved!", output.getMessage());
        assertFalse(output.isRedirectHome());
        assertNotNull(output.getSet());
        assertEquals(2, output.getSet().getFlashcards().size());
    }

    @Test
    void testDeleteSetOutputDataFields() {
        FlashCardSet existing = new FlashCardSet("Delete Test", new ArrayList<>(), 25);
        dataAccess.storedSet = existing;
        viewManagerModel.setCurrentFlashCardSetId(25);

        CreateFlashcardInteractor interactor = new CreateFlashcardInteractor(dataAccess, presenter, viewManagerModel);

        interactor.deleteSet();

        // Verify all output data fields for delete
        CreateFlashcardOutputData output = presenter.received;
        assertEquals(25, output.getSetId());
        assertNull(output.getSetName());
        assertTrue(output.isSuccess());
        assertEquals("Set deleted!", output.getMessage());
        assertTrue(output.isRedirectHome());
    }
}