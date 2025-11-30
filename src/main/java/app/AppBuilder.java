package app;

import data_access.JsonFlashcardSetDataAccessObject;
import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardPresenter;
import interface_adapter.create_flashcard.CreateFlashcardViewModel;
import use_case.create_flashcard.CreateFlashcardDataAccessInterface;
import use_case.create_flashcard.CreateFlashcardInputBoundary;
import use_case.create_flashcard.CreateFlashcardInteractor;
import view.CreateFlashcardView;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel(new CardLayout());

    public AppBuilder addCreateFlashcardView() {

        // 1. Data Access
        CreateFlashcardDataAccessInterface dataAccess =
                new JsonFlashcardSetDataAccessObject();

        // 2. ViewModel
        CreateFlashcardViewModel viewModel = new CreateFlashcardViewModel();

        // 3. Presenter
        CreateFlashcardPresenter presenter = new CreateFlashcardPresenter(viewModel);

        // 4. Interactor
        CreateFlashcardInputBoundary interactor =
                new CreateFlashcardInteractor(dataAccess, presenter);

        // 5. Controller
        CreateFlashcardController controller =
                new CreateFlashcardController(interactor);

        // 6. View
        CreateFlashcardView view =
                new CreateFlashcardView(viewModel, controller);

        // 7. Add to cardPanel
        cardPanel.add(view, view.getViewName());

        return this;
    }

    /**
     * Build the final application window
     */
    public JFrame build() {
        JFrame frame = new JFrame("Flashcard App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.add(cardPanel);
        frame.setLocationRelativeTo(null);
        return frame;
    }
}