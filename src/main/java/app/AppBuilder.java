package app;

import data_access.FileUserDataAccessObject;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.ChangePasswordPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsPresenter;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.review_flashcards.ReviewFlashCardsInteractor;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.LoggedInView;
import view.LoginView;
import view.ReviewFlashCardsView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;

public class AppBuilder {

    public JFrame buildCreateFlashcardUI() {

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new
    // DBUserDataAccessObject(userFactory);

        CreateFlashcardPresenter presenter =
                new CreateFlashcardPresenter();

    private ReviewFlashCardsViewModel reviewFlashCardsViewModel;
    private ReviewFlashCardsView reviewFlashCardsView;
    private ReviewFlashCardsController reviewFlashCardsController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }
      
    // TODO: Make conform to builder pattern!
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

    public AppBuilder addReviewFlashCardsView() {
        reviewFlashCardsViewModel = new ReviewFlashCardsViewModel();
        // TODO: Data loading logic
        reviewFlashCardsViewModel.setState(ReviewFlashCardsView.generateMockViewModel().getState());

        ReviewFlashCardsPresenter reviewFlashCardsPresenter = new ReviewFlashCardsPresenter(reviewFlashCardsViewModel);
        ReviewFlashCardsInteractor reviewFlashCardsInteractor = new ReviewFlashCardsInteractor(
                reviewFlashCardsPresenter);
        reviewFlashCardsController = new ReviewFlashCardsController(reviewFlashCardsInteractor);
        reviewFlashCardsView = new ReviewFlashCardsView(reviewFlashCardsViewModel, reviewFlashCardsController);
        cardPanel.add(reviewFlashCardsView, reviewFlashCardsView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

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
        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new ChangePasswordPresenter(viewManagerModel,
                loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor = new ChangePasswordInteractor(userDataAccessObject,
                changePasswordOutputBoundary, userFactory);

        ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
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

        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(reviewFlashCardsView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }

}
