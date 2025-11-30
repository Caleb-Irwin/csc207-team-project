package app;

import javax.swing.*;
import view.HomePage;
import view.ReviewFlashcards;

public class Main {
    public static void main(String[] args) {
         ReviewFlashcards reviewFlashcards = new ReviewFlashcards();
         reviewFlashcards.reviewFlashcardsMock();
         HomePage.homepageMock();

         AppBuilder app = new AppBuilder();
        app.addCreateFlashcardView();
        JFrame frame = app.build();

        frame.setVisible(true);
    }
}
