package use_case.navigation;

import entity.FlashCardSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.FlashCardSetsDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for NavigationInteractor to achieve 100% line coverage
 */
class NavigationInteractorTest {

    // Fake presenter just records method calls
    static class FakePresenter implements NavigationOutputBoundary {
        boolean promptPageCalled = false;
        boolean settingsPageCalled = false;
        boolean presentSetCalled = false;
        int presentSetId = -1;
        boolean createSetCalled = false;
        int createSetId = -1;
        boolean presentExistingSetsCalled = false;
        List<Map.Entry<String, Integer>> receivedSetInfos = null;

        @Override
        public void presentPromptPage() {
            promptPageCalled = true;
        }

        @Override
        public void presentSettingsPage() {
            settingsPageCalled = true;
        }

        @Override
        public void presentSet(int setId) {
            presentSetCalled = true;
            presentSetId = setId;
        }

        @Override
        public void presentCreateSetPage(int newSetId) {
            createSetCalled = true;
            createSetId = newSetId;
        }

        @Override
        public void presentExistingSets(List<Map.Entry<String, Integer>> setInfos) {
            presentExistingSetsCalled = true;
            receivedSetInfos = setInfos;
        }
    }

    // Fake DAO for testing
    static class FakeDAO implements FlashCardSetsDataAccessInterface {
        List<FlashCardSet> sets = new ArrayList<>();
        int nextId = 1;

        @Override
        public ArrayList<FlashCardSet> getFlashCardSets() {
            return new ArrayList<>(sets);
        }

        @Override
        public FlashCardSet getFlashCardSetById(Integer setId) {
            return null;
        }

        @Override
        public void createFlashCardSet(FlashCardSet flashCardSet) {
            sets.add(flashCardSet);
        }

        @Override
        public void updateFlashCardSet(Integer id, FlashCardSet flashCardSet) {

        }

        @Override
        public void deleteFlashCardSet(Integer id) {

        }

        @Override
        public void registerCallBackOnUpdate(Runnable callback) {

        }

        @Override
        public int getNextAvailableId() {
            return nextId++;
        }
    }

    private FakePresenter presenter;
    private FakeDAO dao;
    private NavigationInteractor interactor;

    @BeforeEach
    void setUp() {
        presenter = new FakePresenter();
        dao = new FakeDAO();
        interactor = new NavigationInteractor(presenter, dao);
    }

    @Test
    void testGoToPromptPage() {
        interactor.goToPromptPage();
        assertTrue(presenter.promptPageCalled);
    }

    @Test
    void testGoToSettingsPage() {
        interactor.goToSettingsPage();
        assertTrue(presenter.settingsPageCalled);
    }

    @Test
    void testGoToSet() {
        interactor.goToSet(42);
        assertTrue(presenter.presentSetCalled);
        assertEquals(42, presenter.presentSetId);
    }

    @Test
    void testGoToCreateSetPage() {
        dao.nextId = 10;
        interactor.goToCreateSetPage();
        assertTrue(presenter.createSetCalled);
        assertEquals(10, presenter.createSetId);
    }

    @Test
    void testLoadExistingSetsEmpty() {
        interactor.loadExistingSets();
        assertTrue(presenter.presentExistingSetsCalled);
        assertNotNull(presenter.receivedSetInfos);
        assertTrue(presenter.receivedSetInfos.isEmpty());
    }

    @Test
    void testLoadExistingSetsWithSets() {
        dao.sets.add(new FlashCardSet("Set A", new ArrayList<>(), 1));
        dao.sets.add(new FlashCardSet("Set B", new ArrayList<>(), 2));

        interactor.loadExistingSets();

        assertTrue(presenter.presentExistingSetsCalled);
        assertNotNull(presenter.receivedSetInfos);
        assertEquals(2, presenter.receivedSetInfos.size());
    }
}
