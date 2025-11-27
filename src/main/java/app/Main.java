package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationPresenter;
import use_case.navigation.NavigationInputBoundary;
import use_case.navigation.NavigationInteractor;
import use_case.navigation.NavigationOutputBoundary;
import view.mainFrame;

public class Main {
    public static void main(String[] args) {
        // Create ViewManagerModel
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // Create Presenter
        NavigationOutputBoundary presenter = new NavigationPresenter(viewManagerModel);

        // Create Interactor
        NavigationInputBoundary interactor = new NavigationInteractor(presenter);

        // Create Controller
        NavigationController navigationController = new NavigationController(interactor);

        // Create and show mainFrame
        new mainFrame(navigationController, viewManagerModel);
    }
}
