package interface_adapter.navigation;

import interface_adapter.ViewManagerModel;
import use_case.navigation.NavigationOutputBoundary;
import view.SidebarView;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * The Navigation presenter
 */

public class NavigationPresenter implements NavigationOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SidebarView sidebarView;

    public NavigationPresenter(ViewManagerModel viewManagerModel, SidebarView sidebarView) {
        this.viewManagerModel = viewManagerModel;
        this.sidebarView = sidebarView;
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
    public void presentSet(int setId) {
        viewManagerModel.setCurrentFlashCardSetId(setId);
        viewManagerModel.setState("review flashcards");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentCreateSetPage(int newSetId) {
        viewManagerModel.setCurrentFlashCardSetId(newSetId);
        viewManagerModel.setState("create flashcard");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentExistingSets(List<Map.Entry<String, Integer>> setInfos) {
        SwingUtilities.invokeLater(() -> {
            if (setInfos == null) {
                return;
            }
            sidebarView.updateSidebarButtons(setInfos);
        });

    }
}
