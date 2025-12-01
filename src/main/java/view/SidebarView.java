package view;

import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Implements the sidebar function of the application
 */
public class SidebarView extends JPanel {
    // have flash card ai header -done
    // when + clicked, go to prompt page -done
    // when generate clicked create a new set jbutton should be -done
    //when a set jbutton is clicked, go to the flash cards button
    // have a settings button -done
    //when settings is clicked (not my job)
    //when a new flashcard is created with an unrecognixed set name, create a new set
    private final JButton newSetButton;
    private final JButton settingsButton;
    private final NavigationController controller;
    private final JPanel scrollContent;
    private final JPanel bottomPanel;
    private final JButton generateSetButton;

    public SidebarView(NavigationController controller) {
        this.controller = controller;

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
        generateSetButton.setBackground(new Color(180, 180, 180));
        generateSetButton.setFocusPainted(false);
        generateSetButton.setBorderPainted(false);
        generateSetButton.setOpaque(true);
        generateSetButton.setPreferredSize(new Dimension(92, 16));
        generateSetButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        //  make white
        newSetButton = new JButton("+ New Set");
        newSetButton.setFont(newSetButton.getFont().deriveFont(Font.BOLD, 14f));
        newSetButton.setBackground(new Color(180, 180, 180));
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
        newSetButton.addActionListener(e -> controller.goToPromptPage());

        // action listener to go to prompt page when the user wants to go to generator page
        generateSetButton.addActionListener(e -> controller.goToPromptPage());

        // action listener to open the settings page when a user clicks settings
        settingsButton.addActionListener(e -> controller.openSettings());
    }


    // add set button called by generating or saving a new set
    public void addSetButton(String setName) {

        JButton setButton = new JButton(setName);
        setButton.setFont(setButton.getFont().deriveFont(Font.BOLD, 14f));

        // make white
        setButton.setBackground(new Color(180, 180, 180));
        setButton.setFocusPainted(false);
        setButton.setBorderPainted(false);
        setButton.setOpaque(true);
        setButton.setPreferredSize(new Dimension(95, 25));
        setButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // this will change to LoadSet
        setButton.addActionListener(e -> controller.loadSet(setName));

        scrollContent.add(setButton);
        revalidate();
        repaint();
    }
}
