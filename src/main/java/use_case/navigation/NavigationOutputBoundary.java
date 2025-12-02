package use_case.navigation;

import java.util.List;
import java.util.Map;

/**
 * The Output Boundary Interface of the Navigation Use Case
 */

public interface NavigationOutputBoundary {
    void presentPromptPage();

    void presentSettingsPage();

    void presentSet(int setId);

    void presentCreateSetPage(int newSetId);

    void presentExistingSets(List<Map.Entry<String, Integer>> setInfos);
}
