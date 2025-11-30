package app;


import data_access.JsonDataAccessObject;

import interface_adapter.ViewManagerModel;
import interface_adapter.generate_flashcard.*;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;

import use_case.generate_flashcard.GeneratorInputBoundary;
import use_case.generate_flashcard.GeneratorInteractor;
import use_case.generate_flashcard.GeneratorOutputBoundary;

import use_case.navigation.NavigationInputBoundary;
import use_case.navigation.NavigationInteractor;
import use_case.navigation.NavigationOutputBoundary;
import use_case.review_flashcards.ReviewFlashCardsInteractor;

import view.*;

import javax.swing.*;
import java.awt.*;

// Create Flashcard imports
import data_access.JsonFlashcardSetDataAccessObject;
import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardPresenter;
import interface_adapter.create_flashcard.CreateFlashcardView;
import use_case.create_flashcard.CreateFlashcardDataAccessInterface;
import use_case.create_flashcard.CreateFlashcardInputBoundary;
import use_case.create_flashcard.CreateFlashcardInteractor;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final JsonDataAccessObject flashcardDataAccessObject = new JsonDataAccessObject("data/");



    private SidebarView sidebarView;

    private GeneratorView generatorView;
    private GeneratorViewModel generatorViewModel;

    private ReviewFlashCardsViewModel reviewFlashCardsViewModel;
    private ReviewFlashCardsView reviewFlashCardsView;
    private ReviewFlashCardsController reviewFlashCardsController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSidebar() {
        // Create Presenter
        NavigationOutputBoundary presenter = new NavigationPresenter(viewManagerModel);

        // Create Interactor
        NavigationInputBoundary interactor = new NavigationInteractor(presenter);

        // Create Controller
        NavigationController navigationController = new NavigationController(interactor);
        this.sidebarView = new SidebarView(navigationController);
        return this;
    }

    public AppBuilder addGeneratorView() {
        generatorViewModel = new GeneratorViewModel();
        generatorView = new GeneratorView(generatorViewModel);
        cardPanel.add(generatorView, generatorView.getViewName());
        return this;
    }

    public AppBuilder addGeneratorUseCase() {

        final GeneratorOutputBoundary presenter = new GeneratorPresenter(
                generatorViewModel, viewManagerModel);
        final GeneratorApiCaller apiCaller = new GeneratorApiCaller();
        final GeneratorJsonParser parser = new GeneratorJsonParser();
        final GeneratorSetSaver saver = new GeneratorSetSaver();

        final GeneratorInputBoundary generatorInteractor = new GeneratorInteractor(presenter, apiCaller, parser, saver);

        final GeneratorController generatorController = new GeneratorController(generatorInteractor);
        generatorView.setGeneratorController(generatorController);
        return this;

    }


    public AppBuilder addReviewFlashCardsView() {
        reviewFlashCardsViewModel = new ReviewFlashCardsViewModel();
        // TODO: Data loading logic
        reviewFlashCardsViewModel.setState(ReviewFlashCardsView.generateMockViewModel().getState());

        ReviewFlashCardsPresenter reviewFlashCardsPresenter = new ReviewFlashCardsPresenter(
                reviewFlashCardsViewModel);
        ReviewFlashCardsInteractor reviewFlashCardsInteractor = new ReviewFlashCardsInteractor(
                reviewFlashCardsPresenter);
        reviewFlashCardsController = new ReviewFlashCardsController(reviewFlashCardsInteractor);
        reviewFlashCardsView = new ReviewFlashCardsView(reviewFlashCardsViewModel, reviewFlashCardsController);
        cardPanel.add(reviewFlashCardsView, reviewFlashCardsView.getViewName());
        return this;
    }

    public JFrame buildCreateFlashcardUI() {

        CreateFlashcardDataAccessInterface dataAccess = new JsonFlashcardSetDataAccessObject();

        CreateFlashcardPresenter presenter = new CreateFlashcardPresenter();

        CreateFlashcardInputBoundary interactor = new CreateFlashcardInteractor(dataAccess);

        CreateFlashcardController controller = new CreateFlashcardController(interactor);

        CreateFlashcardView view = new CreateFlashcardView(controller);

        JFrame frame = new JFrame("Create Flashcard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.add(view);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }


    public JFrame build() {
        final JFrame application = new JFrame("Flash AI");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(1000, 700);
        application.setLocationRelativeTo(null);

        JPanel cardContainer = new JPanel(new BorderLayout());

        cardContainer.add(sidebarView, BorderLayout.WEST);
        cardContainer.add(cardPanel, BorderLayout.CENTER);

        application.add(cardContainer);

        viewManagerModel.setState(generatorView.getViewName());
        viewManagerModel.firePropertyChange();

        application.setVisible(true);

        return application;
    }

}