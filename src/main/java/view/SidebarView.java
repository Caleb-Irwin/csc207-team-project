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

    public SidebarView(NavigationController controller) {
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
        public void addSetButton(String setName) {
            JButton setButton = new JButton(setName);

            // this will change to LoadSet
            setButton.addActionListener(e -> controller.goToPromptPage());

            scrollContent.add(setButton);
            revalidate();
            repaint();
        }
}
