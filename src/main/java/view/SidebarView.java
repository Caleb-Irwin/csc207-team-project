package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final JButton generateSetButton;
    private final ArrayList<Integer> existingSetIds = new ArrayList<>();


    public SidebarView(NavigationController controller, ViewManagerModel viewManagerModel) {
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        controller.loadExistingSets();
        viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));

        // create the panel that stores the content in the scroll pane
        scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(new Color(229, 115, 180));

        // create the scroll Pane
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 115, 180)));

        // create the newSet and settings buttons and the flash AI label
        JLabel flashAILabel = new JLabel("Flash AI");
        flashAILabel.setFont(flashAILabel.getFont().deriveFont(Font.BOLD, 14f));

        // make white
        generateSetButton = new JButton("Generate Set");
        generateSetButton.setFont(generateSetButton.getFont().deriveFont(Font.BOLD, 14f));
        generateSetButton.setBackground(new Color(250, 250, 250));
        generateSetButton.setFocusPainted(false);
        generateSetButton.setBorderPainted(false);
        generateSetButton.setOpaque(true);
        generateSetButton.setPreferredSize(new Dimension(92, 16));
        generateSetButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        //  make white
        newSetButton = new JButton("+ New Set");
        newSetButton.setFont(newSetButton.getFont().deriveFont(Font.BOLD, 14f));
        newSetButton.setBackground(new Color(250, 250, 250));
        newSetButton.setFocusPainted(false);
        newSetButton.setBorderPainted(false);
        newSetButton.setOpaque(true);
        newSetButton.setPreferredSize(new Dimension(92, 16));
        newSetButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));


        settingsButton = new JButton("Settings");
        settingsButton.setFont(settingsButton.getFont().deriveFont(Font.BOLD, 14f));
        settingsButton.setBackground(new Color(180, 180, 180));
        settingsButton.setFocusPainted(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setOpaque(true);
        settingsButton.setPreferredSize(new Dimension(100, 25));
        settingsButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));


        // create the bottom Panel that contains settings button
        bottomPanel = new JPanel();
        bottomPanel.add(settingsButton);
        settingsButton.setAlignmentX(settingsButton.CENTER_ALIGNMENT);
        bottomPanel.setBackground(new Color(229, 115, 180));

        // add the labels, buttons, and panels to the sidebar View
        this.setBackground(new Color(229, 115, 180));
        this.add(flashAILabel);
        flashAILabel.setAlignmentX(flashAILabel.CENTER_ALIGNMENT);
        this.add(generateSetButton);
        this.add(newSetButton);
        generateSetButton.setAlignmentX(generateSetButton.CENTER_ALIGNMENT);
        newSetButton.setAlignmentX(newSetButton.CENTER_ALIGNMENT);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // action listener to go to prompt page when the user wants to create a new set
        newSetButton.addActionListener(e -> controller.goToCreateSetPage());

        // action listener to go to prompt page when the user wants to go to generator page
        generateSetButton.addActionListener(e -> controller.goToPromptPage());

        // action listener to open the settings page when a user clicks settings
        settingsButton.addActionListener(e -> controller.openSettings());
    }

    // add set button called by generating or saving a new set
    public void addSetButton(String setName, int setId) {
        // Check if ID already exists
        if (existingSetIds.contains(setId)) {
            return;
        }

        JButton setButton = new JButton(setName);
        setButton.setAlignmentX(setButton.CENTER_ALIGNMENT);
        setButton.setFont(setButton.getFont().deriveFont(Font.BOLD, 14f));
        // make white
        setButton.setBackground(new Color(250, 250, 250));
        setButton.setFocusPainted(false);
        setButton.setBorderPainted(false);
        setButton.setOpaque(true);
        setButton.setPreferredSize(new Dimension(95, 25));
        setButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        setButton.putClientProperty("setId", setId);
        existingSetIds.add(setId);

        setButton.addActionListener(e -> controller.loadSet(setId));

        scrollContent.add(setButton);
        revalidate();
        repaint();
    }


    public void updateSidebarButtons(List<Map.Entry<String, Integer>> setInfos) {
        // Clear existing
        scrollContent.removeAll();
        existingSetIds.clear();

        // Add all sets
        for (Map.Entry<String, Integer> info : setInfos) {
            String setName = info.getKey();
            int setId = info.getValue();
            addSetButton(setName, setId);
        }

        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            String newState = (String) evt.getNewValue();

            if (newState != null && newState.startsWith("review flashcards")) {
                controller.loadExistingSets();
            }
        }
    }
}
