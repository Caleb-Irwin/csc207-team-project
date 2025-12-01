package use_case.navigation;

/**
 *  The Input Boundary Interface of the Navigation Use Case
 */
public interface NavigationInputBoundary {
    void goToPromptPage();
    void goToSettingsPage();
    void goToSet(int setId);
    void goToCreateSetPage();
}
