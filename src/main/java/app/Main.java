package app;

import javax.swing.*;

import view.HomePage;
import view.ReviewFlashcards;

public class Main {
    public static void main(String[] args) {
        ReviewFlashcards reviewFlashcards = new ReviewFlashcards();
        reviewFlashcards.reviewFlashcardsMock();

         AppBuilder appBuilder = new AppBuilder();
         JFrame application = appBuilder
                 .addHomePage()
        // .addLoginView()
        // .addSignupView()
        // .addLoggedInView()
        // .addSignupUseCase()
        // .addLoginUseCase()
        // .addChangePasswordUseCase()
        // .addLogoutUseCase()
                 .addSettingsView()
         .build();

         application.pack();
         application.setLocationRelativeTo(null);
         application.setVisible(true);
    }
}
