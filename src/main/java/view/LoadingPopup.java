package view;

import javax.swing.*;
import java.awt.*;

public class LoadingPopup extends JDialog {

    // --- Constructor now accepts any Component (like the GeneratorView panel) ---
    public LoadingPopup(Component owner) {
        // Use SwingUtilities to find the top-level Window (Frame) of the component.
        // We explicitly cast the result to Frame to resolve the compilation error.
        super((Frame) SwingUtilities.getWindowAncestor(owner), "Generating Flashcards...", true);

        // Use BorderLayout for simple center alignment
        setLayout(new BorderLayout());

        // Create a panel to hold the components
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Text label
        JLabel loadingText = new JLabel("Almost done...");
        loadingText.setFont(loadingText.getFont().deriveFont(Font.BOLD, 14f));
        contentPanel.add(loadingText);

        // Indeterminate JProgressBar for visual loading feedback
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(250, 20));
//        contentPanel.add(progressBar);

        add(contentPanel, BorderLayout.CENTER);

        // Set size and center the dialog
        setSize(400, 120);
        // Center the dialog relative to its owner (the main frame)
        setLocationRelativeTo(getOwner());

        // Prevent closing by window controls while loading
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public void showLoading() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void hideLoading() {
        SwingUtilities.invokeLater(() -> dispose());
    }
}
