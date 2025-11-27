package interface_adapter.navigation;

import use_case.Navigation.NavigationOutputBoundary;

/**
 * The Controller for the create_set use case
 */

public class NavigationController {
    private final NavigationOutputBoundary loadSetUseCase;

    public NavigationController(NavigationOutputBoundary loadSetUseCase) {
        this.loadSetUseCase = loadSetUseCase;
    }

    public void goToPromptPage() {
        loadSetUseCase.presentPromptPage();
    }

    public void openSettings() {
        loadSetUseCase.presentSettingsPage();
    }
}
