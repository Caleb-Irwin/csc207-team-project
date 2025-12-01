package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Implements the sidebar function of the application
 */
public class SidebarView extends JPanel implements PropertyChangeListener {
    private final JButton newSetButton;
    private final JButton settingsButton;
    private final NavigationController controller;
    private final JPanel scrollContent;
    private final JPanel bottomPanel;
    private final ViewManagerModel viewManagerModel;

    public SidebarView(NavigationController controller, ViewManagerModel viewManagerModel) {
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));

        // create the panel that stores the content in the scroll pane
        scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(new Color(255, 140, 250));

        // create the scroll Pane
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 250)));

        // create the newSet and settings buttons and the flash AI label
        JLabel flashAILabel = new JLabel("Flash AI");
        newSetButton = new JButton("+");
        settingsButton = new JButton("Settings");

        // create the bottom Panel that contains settings button
        bottomPanel = new JPanel();
        bottomPanel.add(settingsButton);
        settingsButton.setAlignmentX(settingsButton.CENTER_ALIGNMENT);
        bottomPanel.setBackground(new Color(255, 140, 250));

        // add the labels, buttons, and panels to the sidebar View
        this.setBackground(new Color(255, 140, 250));
        this.add(flashAILabel);
        flashAILabel.setAlignmentX(flashAILabel.CENTER_ALIGNMENT);
        this.add(newSetButton);
        newSetButton.setAlignmentX(newSetButton.CENTER_ALIGNMENT);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // action listener to go to prompt page when the user wants to create a new set
        newSetButton.addActionListener(e -> controller.goToPromptPage());

        // action listener to open the settings page when a user clicks settings
        settingsButton.addActionListener(e -> controller.openSettings());
    }

    // add set button called by generating or saving a new set
    public void addSetButton(String setName, int setId) {
        JButton setButton = new JButton(setName);
        setButton.setAlignmentX(setButton.CENTER_ALIGNMENT);
        setButton.putClientProperty("setId", setId);

        setButton.addActionListener(e -> controller.loadSet(setId));

        scrollContent.add(setButton);
        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            String newState = (String) evt.getNewValue();

            if (newState != null && newState.startsWith("SET_CREATED")) {
                String[] parts = newState.split(":");
                final String setName = parts[1];
                final int setId = Integer.parseInt(parts[2]);

                // Update UI on EDT
                SwingUtilities.invokeLater(() -> {
                    addSetButton(setName, setId);
                    // Clear the state to prevent reprocessing
                    viewManagerModel.setState("");
                });
            }
        }
    }
}
