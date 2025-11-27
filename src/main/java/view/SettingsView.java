package view;

import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    private NavigationController navigationController;

    public SettingsView(NavigationController navigationController) {
        this.navigationController = navigationController;
        this.setLayout(new BorderLayout());

        JPanel rightPanel = new JPanel();
        // NEW BoxLayout tied to this panel only
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(new JLabel("Settings"));
        JTextField box = new JTextField(10);
        rightPanel.add(box);
        box.setMaximumSize(new Dimension(500, 25));
        rightPanel.add(new JButton("Submit"));

        this.add(rightPanel, BorderLayout.CENTER);
    }
}

