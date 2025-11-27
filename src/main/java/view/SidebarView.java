package view;

import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Implements the side bar and its functionality
public class SidebarView extends JPanel {
    // have flash card ai header -done
// when + clicked, go to prompt page -done
    // when generate clicked create a new set jbutton should be done
    //when a set jbutton is clicked, go to the flash cards button
    // have a settings button -done
    //when settings is clicked (not my job)
    //when a new flashcard is created with an unrecognixed set name, create a new set
    private final JButton newSetButton;
    private final JButton settingsButton;
    private final NavigationController controller;

    public SidebarView(NavigationController controller) {
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel flashAILabel = new JLabel("Flash AI");
        newSetButton = new JButton("+");
        settingsButton = new JButton("Settings");
        this.add(flashAILabel);
        this.add(newSetButton);
        this.add(settingsButton, BorderLayout.SOUTH);

        // action listener to go to prompt page when the user wants to create a new set
        newSetButton.addActionListener(e -> controller.goToPromptPage());
        // action listener to open the settings page when a user clicks settings
        settingsButton.addActionListener(e -> controller.openSettings());
    }
        public void addSetButton(String setName, ActionListener listener) {
            JButton setButton = new JButton(setName);
            setButton.addActionListener(listener);
            add(setButton);
            revalidate();
            repaint();
        }
}
