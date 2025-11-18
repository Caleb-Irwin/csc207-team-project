package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
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
}
