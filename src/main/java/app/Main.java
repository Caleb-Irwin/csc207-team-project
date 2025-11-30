package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder.addSidebar()
                .addReviewFlashCardsView()
                .addGeneratorView()
                .addGeneratorUseCase()
                .build();
                .addCreateFlashcardView();
        // appBuilder.buildCreateFlashcardUI();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
//        // Build application with your Create Flashcard view
//        AppBuilder app = new AppBuilder();
//        app.addCreateFlashcardView();  // add your use case view
//        JFrame frame = app.build();
//
//        frame.setVisible(true);
    }
}