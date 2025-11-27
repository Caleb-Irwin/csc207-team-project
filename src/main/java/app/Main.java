package app;

import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationPresenter;
import use_case.Navigation.NavigationInputBoundary;
import use_case.Navigation.NavigationOutputBoundary;
import view.HomePage;
import view.ReviewFlashcards;
import view.mainFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
//        ReviewFlashcards reviewFlashcards = new ReviewFlashcards();
//        reviewFlashcards.reviewFlashcardsMock();
        // Create ViewManagerModel
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // Create Presenter
        NavigationOutputBoundary presenter = new NavigationPresenter(viewManagerModel);

        // Create Controller
        NavigationController navigationController = new NavigationController(presenter);

        // Create and show HomePage
        new mainFrame(navigationController, viewManagerModel);
        // HomePage now handles everything internally
        // HomePage.homepageMock();

        // AppBuilder appBuilder = new AppBuilder();
        // JFrame application = appBuilder
        // .addLoginView()
        // .addSignupView()
        // .addLoggedInView()
        // .addSignupUseCase()
        // .addLoginUseCase()
        // .addChangePasswordUseCase()
        // .addLogoutUseCase()
        // .build();

        // application.pack();
        // application.setLocationRelativeTo(null);
        // application.setVisible(true);
    }
}
