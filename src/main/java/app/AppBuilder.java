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

import view.GeneratorView;
import view.ReviewFlashCardsView;
import view.SidebarView;
import view.ViewManager;

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
        NavigationOutputBoundary presenter = new NavigationPresenter(viewManagerModel);
        NavigationInputBoundary interactor = new NavigationInteractor(presenter);
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
        GeneratorOutputBoundary presenter = new GeneratorPresenter(generatorViewModel, viewManagerModel);
        GeneratorApiCaller apiCaller = new GeneratorApiCaller(DAO);
        GeneratorJsonParser parser = new GeneratorJsonParser();
        GeneratorSetSaver saver = new GeneratorSetSaver();

        GeneratorInputBoundary interactor =
                new GeneratorInteractor(presenter, apiCaller, parser, saver);
        GeneratorController controller = new GeneratorController(interactor);

        generatorView.setGeneratorController(controller);
        return this;
    }


    public AppBuilder addReviewFlashCardsView() {
        reviewFlashCardsViewModel = new ReviewFlashCardsViewModel();

        ReviewFlashCardsPresenter presenter = new ReviewFlashCardsPresenter(reviewFlashCardsViewModel);
        ReviewFlashCardsInteractor interactor =
                new ReviewFlashCardsInteractor(presenter, DAO, viewManagerModel);

        reviewFlashCardsController = new ReviewFlashCardsController(interactor);

        reviewFlashCardsView = new ReviewFlashCardsView(
                reviewFlashCardsViewModel,
                reviewFlashCardsController,
                DAO,
                viewManagerModel
        );

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
}
