package use_case.Navigation;

public class NavigationInteractor implements NavigationInputBoundary {
    private final NavigationOutputBoundary presenter;

    public NavigationInteractor(NavigationOutputBoundary presenter) {

        this.presenter = presenter;
    }

    @Override
    public void goToPromptPage() {
        presenter.presentPromptPage();
    }

    public void goToSettingsPage() {
        presenter.presentSettingsPage();
    }
}
