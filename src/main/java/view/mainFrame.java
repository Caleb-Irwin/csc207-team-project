package view;


import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;

public class mainFrame extends JFrame {

    private final ViewManager viewManager;
    private final ViewManagerModel viewManagerModel;
    private final NavigationController navigationController;
    private final SidebarView sidebar;


    public mainFrame(NavigationController navigationController, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.navigationController = navigationController;

        this.setTitle("Study App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null); // center on screen

        // Initialize sidebar
        this.sidebar = new SidebarView(navigationController);
        this.add(sidebar, BorderLayout.WEST);

        // Create the card container panel
        JPanel cardContainer = new JPanel(new CardLayout());

        // Create ViewManagerModel and ViewManager
        viewManager = new ViewManager(cardContainer, (CardLayout) cardContainer.getLayout(), viewManagerModel);

        // Add initial pages
        cardContainer.add(new HomePage(navigationController), "PROMPT_PAGE");

        cardContainer.add(new SettingsView(navigationController), "SETTINGS_PAGE");

        this.add(cardContainer, BorderLayout.CENTER); // add the card container to the JFrame

         viewManagerModel.setState("PROMPT_PAGE");
         viewManagerModel.firePropertyChange();

        this.setVisible(true);
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}

