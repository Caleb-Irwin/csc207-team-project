package app;

import data_access.JsonFlashcardSetDataAccessObject;
import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardPresenter;
import interface_adapter.create_flashcard.CreateFlashcardView;

import use_case.create_flashcard.CreateFlashcardDataAccessInterface;
import use_case.create_flashcard.CreateFlashcardInputBoundary;
import use_case.create_flashcard.CreateFlashcardInteractor;

import javax.swing.*;

public class AppBuilder {

    public JFrame buildCreateFlashcardUI() {

        CreateFlashcardDataAccessInterface dataAccess =
                new JsonFlashcardSetDataAccessObject();

        CreateFlashcardPresenter presenter =
                new CreateFlashcardPresenter();

        CreateFlashcardInputBoundary interactor =
                new CreateFlashcardInteractor(dataAccess);

        CreateFlashcardController controller =
                new CreateFlashcardController(interactor);

        CreateFlashcardView view =
                new CreateFlashcardView(controller);

        JFrame frame = new JFrame("Create Flashcard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.add(view);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
