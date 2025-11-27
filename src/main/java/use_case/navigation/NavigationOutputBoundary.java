package use_case.navigation;

public interface NavigationOutputBoundary {
    void presentPromptPage();
    void presentSettingsPage();
    void presentSet(String setName);
}
