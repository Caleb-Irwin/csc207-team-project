// package use_case;

// import entity.FlashCard;
// import entity.FlashCardSet;
// import org.junit.jupiter.api.Test;
// import use_case.create_flashcard.*;

// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// /**
// * Unit tests for CreateFlashcardInteractor
// */
// class CreateFlashcardInteractorTest {

// /**
// * Fake Data Access — in-memory stand-in for JsonDataAccessObject
// */
// private static class FakeFlashCardDataAccess implements
// FlashCardSetsDataAccessInterface {

// FlashCardSet storedSet = null;
// Integer deletedId = null;

// @Override
// public ArrayList<FlashCardSet> getFlashCardSets() {
// return null;
// }

// @Override
// public FlashCardSet getFlashCardSetById(Integer setId) {
// if (storedSet != null && storedSet.getId().equals(setId)) {
// return storedSet;
// }
// return null;
// }

// @Override
// public void createFlashCardSet(FlashCardSet flashCardSet) {
// this.storedSet = flashCardSet;
// }

// @Override
// public void updateFlashCardSet(Integer id, FlashCardSet flashCardSet) {
// this.storedSet = flashCardSet;
// }

// @Override
// public void deleteFlashCardSet(Integer id) {
// this.deletedId = id;
// this.storedSet = null;
// }

// @Override
// public void registerCallBackOnUpdate(Runnable callback) {}

// @Override
// public int getNextAvailableId() {
// return 1;
// }
// }

// /**
// * Fake Presenter — captures the output passed by interactor
// */
// private static class FakePresenter implements CreateFlashcardOutputBoundary {
// CreateFlashcardOutputData received;

// @Override
// public void present(CreateFlashcardOutputData outputData) {
// received = outputData;
// }
// }

// @Test
// void testUpdateExistingFlashcardSet() {

// FakeFlashCardDataAccess data = new FakeFlashCardDataAccess();
// FakePresenter presenter = new FakePresenter();

// // --- Existing set already in data ---
// FlashCardSet existing = new FlashCardSet("Old Name", new ArrayList<>(), 5);
// existing.addFlashcard(new FlashCard("OldQ", "OldA"));
// data.storedSet = existing;

// CreateFlashcardInteractor interactor =
// new CreateFlashcardInteractor(data, presenter);

// List<String> q = List.of("NewQ1", "NewQ2");
// List<String> a = List.of("NewA1", "NewA2");

// CreateFlashcardInputData input =
// new CreateFlashcardInputData(5, "Updated Name", q, a);

// interactor.saveFlashcards(input);

// // --- Assertions ---
// assertNotNull(data.storedSet);
// assertEquals("Updated Name", data.storedSet.getSetName());
// assertEquals(2, data.storedSet.getFlashcards().size());
// assertEquals("NewQ1", data.storedSet.getFlashcards().get(0).getQuestion());

// assertEquals("Set saved!", presenter.received.getMessage());
// }

// @Test
// void testSaveNewFlashcardSet() {

// FakeFlashCardDataAccess data = new FakeFlashCardDataAccess();
// FakePresenter presenter = new FakePresenter();

// CreateFlashcardInteractor interactor =
// new CreateFlashcardInteractor(data, presenter);

// List<String> q = List.of("Q1", "Q2");
// List<String> a = List.of("A1", "A2");

// CreateFlashcardInputData input =
// new CreateFlashcardInputData(1, "My Set", q, a);

// interactor.saveFlashcards(input);

// assertNotNull(data.storedSet);
// assertEquals("My Set", data.storedSet.getSetName());
// assertEquals(2, data.storedSet.getFlashcards().size());
// assertEquals("Set saved!", presenter.received.getMessage());
// }

// @Test
// void testDeleteExistingSet() {

// FakeFlashCardDataAccess data = new FakeFlashCardDataAccess();
// FakePresenter presenter = new FakePresenter();

// data.storedSet = new FlashCardSet("Old Set", new ArrayList<>(), 1);

// CreateFlashcardInteractor interactor =
// new CreateFlashcardInteractor(data, presenter);

// interactor.deleteSet(1);

// assertNull(data.storedSet);
// assertEquals(1, data.deletedId);
// assertEquals("Set deleted!", presenter.received.getMessage());
// }

// @Test
// void testDeleteNonExistingSet() {

// FakeFlashCardDataAccess data = new FakeFlashCardDataAccess();
// FakePresenter presenter = new FakePresenter();

// CreateFlashcardInteractor interactor =
// new CreateFlashcardInteractor(data, presenter);

// interactor.deleteSet(99);

// assertEquals("Set does not exist.", presenter.received.getMessage());
// }
// }