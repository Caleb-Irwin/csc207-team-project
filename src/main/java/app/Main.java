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
         app.buildCreateFlashcardUI();

        JFrame frame = new JFrame("Create Flashcard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        frame.setVisible(true);
    }
}
