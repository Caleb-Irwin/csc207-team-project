package view;

import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private final NavigationController navigationController;

    public HomePage(NavigationController navigationController) {
        this.navigationController = navigationController;
        // Set up main Layout
        this.setLayout(new BorderLayout());

        this.setSize(1000, 700);
        this.setLocation(650, 500);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel question = new JLabel("What do you want to learn today?");
        JTextField prompt = new JTextField(10);
        prompt.setMaximumSize(new Dimension(500, 30));
        JButton generate = new JButton("Generate");
        rightPanel.add(question);
        rightPanel.add(prompt);
        rightPanel.add(generate);

        this.add(rightPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}

    /**
    public static void homepageMock() {
        SwingUtilities.invokeLater(() -> {
            JPanel main = new JPanel();

            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            JLabel question = new JLabel("What do you want to learn today?");
            JTextField prompt = new JTextField(10);
            JButton generate = new JButton("Generate");
            rightPanel.add(question);
            rightPanel.add(prompt);
            rightPanel.add(generate); // make action listener

            JPanel flashAI = new JPanel();
            flashAI.setLayout(new BoxLayout(flashAI, BoxLayout.Y_AXIS));
            JLabel flashAILabel = new JLabel("Flash AI");
            JButton newset = new JButton("+ New Set"); // make action listener
            JButton settings = new JButton("Settings"); // make action listener
            flashAI.add(flashAILabel);
            flashAI.add(newset);
            flashAI.add(settings);

            main.add(flashAI);
            main.add(rightPanel);

            JFrame frame = new JFrame("HomePage");
            frame.setSize(1000, 700);
            frame.setLocation(650, 500);
            frame.setContentPane(main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
} */
