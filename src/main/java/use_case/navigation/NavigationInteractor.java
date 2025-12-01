package use_case.navigation;

import entity.FlashCardSet;
import use_case.FlashCardSetsDataAccessInterface;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Navigation Interactor
 */
public class NavigationInteractor implements NavigationInputBoundary {
    private final NavigationOutputBoundary presenter;
    private final FlashCardSetsDataAccessInterface dataAccess;

    public NavigationInteractor(NavigationOutputBoundary presenter, FlashCardSetsDataAccessInterface dataAccess) {

        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    @Override
    public void goToPromptPage() {
        presenter.presentPromptPage();
    }
    @Override
    public void goToSettingsPage() {
        presenter.presentSettingsPage();
    }
    @Override
    public void goToSet(int setId) {
        presenter.presentSet(setId);
    }
    @Override
    public void goToCreateSetPage() {
        presenter.presentCreateSetPage();
    }

    @Override
    public void loadExistingSets() {
        List<FlashCardSet> sets = dataAccess.getFlashCardSets();
        List<Map.Entry<String, Integer>> setInfos = new ArrayList<>();

        for (FlashCardSet set : sets) {
            Map.Entry<String, Integer> data = new AbstractMap.SimpleEntry<>(set.getSetName(), set.getId());
            setInfos.add(data);
        }
        presenter.presentExistingSets(setInfos);
    }
}
