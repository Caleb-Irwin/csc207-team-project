package use_case;

import entity.FlashCardSet;

import java.util.ArrayList;

/**
 * Fake Data Access — in-memory stand-in for JsonDataAccessObject.
 * Shared across interactor tests.
 */
public class FakeFlashCardDataAccess implements FlashCardSetsDataAccessInterface {

    public FlashCardSet storedSet = null;
    public Integer deletedId = null;
    public Integer updatedId = null;

    @Override
    public ArrayList<FlashCardSet> getFlashCardSets() {
        ArrayList<FlashCardSet> sets = new ArrayList<>();
        if (storedSet != null) {
            sets.add(storedSet);
        }
        return sets;
    }

    @Override
    public FlashCardSet getFlashCardSetById(Integer setId) {
        if (storedSet != null && storedSet.getId().equals(setId)) {
            return storedSet;
        }
        return null;
    }

    @Override
    public void createFlashCardSet(FlashCardSet flashCardSet) {
        this.storedSet = flashCardSet;
    }

    @Override
    public void updateFlashCardSet(Integer id, FlashCardSet flashCardSet) {
        this.updatedId = id;
        this.storedSet = flashCardSet;
    }

    @Override
    public void deleteFlashCardSet(Integer id) {
        this.deletedId = id;
        this.storedSet = null;
    }

    @Override
    public void registerCallBackOnUpdate(Runnable callback) {
        // Not needed for testing - callbacks are not used in these unit tests
    }

    @Override
    public int getNextAvailableId() {
        return 1;
    }
}
