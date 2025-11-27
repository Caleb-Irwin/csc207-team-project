package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder.addSidebar()
                .addReviewFlashCardsView()
                .addGeneratorView()
                .build();

        // appBuilder.buildCreateFlashcardUI();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}