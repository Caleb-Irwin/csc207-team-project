package app;
import data_access.FileUserDataAccessObject;
import data_access.JsonDataAccessObject;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_flashcard.*;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.ChangePasswordPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.generate_flashcard.GeneratorInputBoundary;
import use_case.generate_flashcard.GeneratorInteractor;
import use_case.generate_flashcard.GeneratorOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.navigation.NavigationInputBoundary;
import use_case.navigation.NavigationInteractor;
import use_case.navigation.NavigationOutputBoundary;
import use_case.review_flashcards.ReviewFlashCardsInteractor;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;


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
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);

    final JsonDataAccessObject flashcardDataAccessObject = new JsonDataAccessObject("data/");

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new
    // DBUserDataAccessObject(userFactory);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
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

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
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
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new ChangePasswordPresenter(
                viewManagerModel,
                loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor = new ChangePasswordInteractor(
                userDataAccessObject,
                changePasswordOutputBoundary, userFactory);

        ChangePasswordController changePasswordController = new ChangePasswordController(
                changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }
    /**
     * Adds the Logout Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject,
                logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
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



//    public AppBuilder addCreateFlashcardView() {
//
//        // 1. Data Access
//        CreateFlashcardDataAccessInterface dataAccess =
//                new JsonFlashcardSetDataAccessObject();
//
//        // 2. ViewModel
//        CreateFlashcardViewModel viewModel = new CreateFlashcardViewModel();
//
//        // 3. Presenter
//        CreateFlashcardPresenter presenter = new CreateFlashcardPresenter(viewModel);
//
//        // 4. Interactor
//        CreateFlashcardInputBoundary interactor =
//                new CreateFlashcardInteractor(dataAccess, presenter);
//
//        // 5. Controller
//        CreateFlashcardController controller =
//                new CreateFlashcardController(interactor);
//
//        // 6. View
//        CreateFlashcardView view =
//                new CreateFlashcardView(viewModel, controller);
//
//        // 7. Add to cardPanel
//        cardPanel.add(view, view.getViewName());
//
//        return this;
//    }
//
//    /**
//     * Build the final application window
//     */
//    public JFrame build() {
//        JFrame frame = new JFrame("Flashcard App");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500, 600);
//        frame.add(cardPanel);
//        frame.setLocationRelativeTo(null);
//        return frame;
//    }
}