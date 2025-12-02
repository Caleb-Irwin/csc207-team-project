package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;
import org.jetbrains.annotations.NotNull;

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
    private transient NavigationController controller;
    private final JPanel scrollContent;
    private final JPanel bottomPanel;
    private final JButton generateSetButton;
    private final JPanel topPanel;
    private final ArrayList<Integer> existingSetIds = new ArrayList<>();

    public SidebarView(NavigationController controller, ViewManagerModel viewManagerModel) {
        this.controller = controller;
        viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(229, 115, 180));

        // create the panel that stores the content in the scroll pane
        scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(new Color(229, 115, 180));

        // create the scroll Pane
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 115, 180)));

        // create a top panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.setBackground(new Color(229, 115, 180));

        // create the newSet and settings buttons and the flash AI label
        JLabel flashAILabel = new JLabel("Flash AI");
        flashAILabel.setFont(flashAILabel.getFont().deriveFont(Font.BOLD, 20f));

        // make white
        generateSetButton = new JButton("Generate Set");
        generateSetButton.setFont(generateSetButton.getFont().deriveFont(Font.BOLD, 17f));
        generateSetButton.setBackground(new Color(170, 150, 200));
        generateSetButton.setFocusPainted(false);
        generateSetButton.setBorderPainted(false);
        generateSetButton.setOpaque(true);
        generateSetButton.setPreferredSize(new Dimension(100, 24));
        generateSetButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // make white
        newSetButton = new JButton("+ New Set");
        newSetButton.setFont(newSetButton.getFont().deriveFont(Font.BOLD, 17f));
        newSetButton.setBackground(new Color(170, 150, 200));
        newSetButton.setFocusPainted(false);
        newSetButton.setBorderPainted(false);
        newSetButton.setOpaque(true);
        newSetButton.setPreferredSize(new Dimension(100, 24));
        newSetButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // add labels and buttons to top panel
        topPanel.add(flashAILabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(generateSetButton);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(newSetButton);
        topPanel.add(Box.createVerticalStrut(10));
        flashAILabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateSetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newSetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.setBackground(new Color(229, 115, 180));

        // add the labels, buttons, and panels to the sidebar View
        this.setBackground(new Color(229, 115, 180));
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // action listener to go to prompt page when the user wants to create a new set
        newSetButton.addActionListener(e -> this.controller.goToCreateSetPage());

        // action listener to go to prompt page when the user wants to go to generator
        // page
        generateSetButton.addActionListener(e -> this.controller.goToPromptPage());

        // action listener to open the settings page when a user clicks settings
        settingsButton.addActionListener(e -> this.controller.openSettings());
    }

    public void setController(NavigationController controller) {
        this.controller = controller;
        // Load existing sets now that we have the proper controller
        controller.loadExistingSets();
    }

    // add set button called by generating or saving a new set
    public void addSetButton(String setName, int setId) {
        // Check if ID already exists
        if (existingSetIds.contains(setId)) {
            return;
        }

        JButton setButton = getJButton(setName);
        setButton.putClientProperty("setId", setId);
        existingSetIds.add(setId);

        setButton.addActionListener(e -> controller.loadSet(setId));

        scrollContent.add(setButton);
        scrollContent.add(Box.createVerticalStrut(10));
        revalidate();
        repaint();
    }

    @NotNull
    private static JButton getJButton(String setName) {
        JButton setButton = new JButton(setName);
        setButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButton.setFont(setButton.getFont().deriveFont(Font.BOLD, 14f));

        setButton.setBackground(new Color(217, 210, 230));
        setButton.setFocusPainted(false);
        setButton.setBorderPainted(false);
        setButton.setOpaque(true);
        setButton.setPreferredSize(new Dimension(95, 25));
        setButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        setButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        setButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return setButton;
    }

    public void updateSidebarButtons(List<Map.Entry<String, Integer>> setInfos) {
        // Clear existing
        scrollContent.removeAll();
        existingSetIds.clear();
        List<Map.Entry<String, Integer>> reversed = new ArrayList<>();

        // Add in reverse order
        for (int i = setInfos.size() - 1; i >= 0; i--) {
            reversed.add(setInfos.get(i));

            // Add all sets
            for (Map.Entry<String, Integer> info : reversed) {
                String setName = info.getKey();
                int setId = info.getValue();
                addSetButton(setName, setId);
            }
        }

        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {

            controller.loadExistingSets();
        }
    }
}
