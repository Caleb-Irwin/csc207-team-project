package interface_adapter.create_flashcard;

import java.util.ArrayList;
import java.util.List;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.create_flashcard.CreateFlashcardOutputBoundary;
import use_case.create_flashcard.CreateFlashcardOutputData;

/**
 * Presenter for handling the Create Flashcard use case.
 */
public class CreateFlashcardPresenter implements CreateFlashcardOutputBoundary {

    private final CreateFlashcardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs the presenter with the necessary view models.
     *
     * @param viewModel the view model to update the view
     * @param viewManagerModel the view manager model to manage view state transitions
     */
    public CreateFlashcardPresenter(CreateFlashcardViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * This method is called to present the results of the Create Flashcard use case.
     * It updates the view model with the data received from the interactor and triggers
     * any necessary view transitions.
     *
     * @param outputData the data containing the results of the use case to be displayed in the view
     */
    @Override
    public void present(CreateFlashcardOutputData outputData) {
        CreateFlashcardState lastState = viewModel.getState();

        CreateFlashcardState state = setCurrentSet(outputData.getSet());
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);
        if (!lastState.getSetName().equals(state.getSetName())) {
            viewManagerModel.firePropertyChange();
        }

        if (outputData.isRedirectHome()) {
            viewManagerModel.setState("generator");
            viewManagerModel.firePropertyChange();
        }
    }

    /**
     * Helper method to set the current set data in the state.
     *
     * @param set the flashcard set to be displayed
     * @return the new state with the flashcard set's details
     */
    private CreateFlashcardState setCurrentSet(FlashCardSet set) {
        CreateFlashcardState state = new CreateFlashcardState();

        if (set != null) {
            state.setSetName(set.getSetName() != null ? set.getSetName() : "");
            List<String> questions = new ArrayList<>();
            List<String> answers = new ArrayList<>();
            for (FlashCard card : set.getFlashcards()) {
                questions.add(card.getQuestion());
                answers.add(card.getAnswer());
            }

            state.setQuestions(questions);
            state.setAnswers(answers);
        }

        return state;
    }
}