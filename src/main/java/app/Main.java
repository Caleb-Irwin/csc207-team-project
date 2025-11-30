package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Build application with your Create Flashcard view
        AppBuilder app = new AppBuilder();
        app.addCreateFlashcardView();  // add your use case view
        JFrame frame = app.build();

        frame.setVisible(true);
    }
}