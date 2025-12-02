package use_case.navigation;

import entity.FlashCardSet;
import use_case.FlashCardSetsDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Unit test for NavigationInteractor to achieve 100% line coverage
 */
public class NavigationInteractorTest {

    // Fake presenter just records method calls
    static class FakePresenter implements NavigationOutputBoundary {
        boolean promptPageCalled = false;
        boolean settingsPageCalled = false;
        boolean presentSetCalled = false;
        boolean createSetCalled = false;
        boolean presentExistingSetsCalled = false;

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
        }

        @Override
        public void presentCreateSetPage(int newSetId) {
            createSetCalled = true;
        }

        @Override
        public void presentExistingSets(List<Map.Entry<String, Integer>> setInfos) {
            presentExistingSetsCalled = true;
        }
    }

    // Fake DAO for testing
    static class FakeDAO implements FlashCardSetsDataAccessInterface {
        List<FlashCardSet> sets = new ArrayList<>();
        int nextId = 1;

        @Override
        public ArrayList<FlashCardSet> getFlashCardSets() {
            return (ArrayList<FlashCardSet>) sets;
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

    public static void main(String[] args) {
        FakePresenter presenter = new FakePresenter();
        FakeDAO dao = new FakeDAO();
        NavigationInteractor interactor = new NavigationInteractor(presenter, dao);

        // Call all interactor methods to cover all lines
        interactor.goToPromptPage();
        interactor.goToSettingsPage();
        interactor.goToSet(1);
        interactor.goToCreateSetPage();
        interactor.loadExistingSets();
    }
}
