package interface_adapter.navigation;

import interface_adapter.ViewManagerModel;
import use_case.navigation.NavigationOutputBoundary;

/**
 * The Navigation presenter
 */

public class NavigationPresenter implements NavigationOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public NavigationPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentPromptPage() {
        viewManagerModel.setState("generator");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentSettingsPage() {
        viewManagerModel.setState("settings");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentSet(String setName) {
        viewManagerModel.setState("SET_PAGE_" + setName);
        viewManagerModel.firePropertyChange();
    }
}
