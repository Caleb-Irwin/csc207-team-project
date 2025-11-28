package use_case.navigation;

/**
 * The Output Boundary Interface of the Navigation Use Case
 */
public interface NavigationOutputBoundary {
    void presentPromptPage();
    void presentSettingsPage();
    void presentSet(String setName);
}
