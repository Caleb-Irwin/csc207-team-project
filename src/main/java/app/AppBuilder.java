package app;

import data_access.JsonDataAccessObject;

import interface_adapter.ViewManagerModel;

import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardPresenter;
import interface_adapter.create_flashcard.CreateFlashcardViewModel;
import interface_adapter.generate_flashcard.*;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationPresenter;

import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;

import use_case.FlashCardSetsDataAccessInterface;
import use_case.create_flashcard.CreateFlashcardInputBoundary;
import use_case.create_flashcard.CreateFlashcardInteractor;
import use_case.generate_flashcard.GeneratorInputBoundary;
import use_case.generate_flashcard.GeneratorInteractor;
import use_case.generate_flashcard.GeneratorOutputBoundary;

import use_case.navigation.NavigationInputBoundary;
import use_case.navigation.NavigationInteractor;
import use_case.navigation.NavigationOutputBoundary;
import use_case.review_flashcards.ReviewFlashCardsInputBoundary;
import use_case.review_flashcards.ReviewFlashCardsInteractor;
import use_case.review_flashcards.ReviewFlashCardsOutputBoundary;
import view.*;

// Create Flashcard imports

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final JsonDataAccessObject DAO = new JsonDataAccessObject("data/");

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
        this.sidebarView = new SidebarView(null, viewManagerModel);

        NavigationOutputBoundary presenter = new NavigationPresenter(viewManagerModel, sidebarView);
        NavigationInputBoundary interactor = new NavigationInteractor(presenter, DAO);
        NavigationController navigationController = new NavigationController(interactor);

        sidebarView.setController(navigationController);

        return this;
    }

    public AppBuilder addGeneratorView() {
        generatorViewModel = new GeneratorViewModel();
        generatorView = new GeneratorView(generatorViewModel);
        cardPanel.add(generatorView, generatorView.getViewName());
        return this;
    }

    public AppBuilder addGeneratorUseCase() {
        GeneratorOutputBoundary presenter = new GeneratorPresenter(generatorViewModel, viewManagerModel,
                reviewFlashCardsViewModel);
        GeneratorApiCaller apiCaller = new GeneratorApiCaller(DAO);
        GeneratorStringParser parser = new GeneratorStringParser();
        GeneratorSetSaver saver = new GeneratorSetSaver(DAO);

        GeneratorInputBoundary interactor = new GeneratorInteractor(presenter, apiCaller, parser, saver);
        GeneratorController controller = new GeneratorController(interactor);

        generatorView.setGeneratorController(controller);
        return this;
    }

    public AppBuilder addReviewFlashCardsUseCase() {
        reviewFlashCardsViewModel = new ReviewFlashCardsViewModel();
        ReviewFlashCardsOutputBoundary presenter = new ReviewFlashCardsPresenter(reviewFlashCardsViewModel,
                viewManagerModel);
        ReviewFlashCardsInputBoundary interactor = new ReviewFlashCardsInteractor(presenter, DAO, viewManagerModel);
        reviewFlashCardsController = new ReviewFlashCardsController(interactor);
        reviewFlashCardsView = new ReviewFlashCardsView(
                reviewFlashCardsViewModel,
                reviewFlashCardsController,
                viewManagerModel);
        return this;
    }

    public AppBuilder addReviewFlashCardsView() {
        cardPanel.add(reviewFlashCardsView, reviewFlashCardsView.getViewName());
        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Flash AI");
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

    public AppBuilder addCreateFlashcardView() {

        CreateFlashcardViewModel viewModel = new CreateFlashcardViewModel();
        CreateFlashcardPresenter presenter = new CreateFlashcardPresenter(viewModel, viewManagerModel);
        CreateFlashcardInputBoundary interactor = new CreateFlashcardInteractor(DAO, presenter, viewManagerModel);
        CreateFlashcardController controller = new CreateFlashcardController(interactor);

        CreateFlashcardView view = new CreateFlashcardView(viewModel, controller, viewManagerModel);

        cardPanel.add(view, view.getViewName());

        return this;
    }

}
