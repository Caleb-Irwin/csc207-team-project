package interface_adapter.navigation;

import use_case.navigation.NavigationInputBoundary;

/**
 * The Controller for the Navigation use case
 */

public class NavigationController {
    private final NavigationInputBoundary interactor;

    public NavigationController(NavigationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void goToPromptPage() {
        interactor.goToPromptPage();
    }

    public void openSettings() {
        interactor.goToSettingsPage();
    }

    public void loadSet(String setName) {
        interactor.goToSet(setName);
    }
}
