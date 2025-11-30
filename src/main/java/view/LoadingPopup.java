package view;

import javax.swing.*;
import java.awt.*;

public class LoadingPopup extends JDialog {


    public LoadingPopup(Component owner) {

        super((Frame) SwingUtilities.getWindowAncestor(owner), "Generating Flashcards...", true);


        setLayout(new BorderLayout());


        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPanel.setBackground(new Color(240, 240, 240));


        JLabel loadingText = new JLabel("Almost done...");
        loadingText.setFont(loadingText.getFont().deriveFont(Font.BOLD, 14f));
        contentPanel.add(loadingText);


        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(250, 20));
//        contentPanel.add(progressBar);

        add(contentPanel, BorderLayout.CENTER);


        setSize(400, 120);

        setLocationRelativeTo(getOwner());


        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public void showLoading() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void hideLoading() {
        SwingUtilities.invokeLater(() -> dispose());
    }
}
