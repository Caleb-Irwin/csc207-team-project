package app;

import javax.swing.*;
import view.HomePage;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder.addReviewFlashCardsView()
                .build();
      
       app.buildCreateFlashcardUI();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
