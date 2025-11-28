package use_case.navigation;

/**
 * The Navigation Interactor
 */
public class NavigationInteractor implements NavigationInputBoundary {
    private final NavigationOutputBoundary presenter;

    public NavigationInteractor(NavigationOutputBoundary presenter) {

        this.presenter = presenter;
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
    public void goToSet(String setName) {
        presenter.presentSet(setName);
    }
}
