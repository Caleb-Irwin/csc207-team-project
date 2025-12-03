package interface_adapter.create_flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.create_flashcard.CreateFlashcardOutputBoundary;
import use_case.create_flashcard.CreateFlashcardOutputData;

/**
 * Presenter for the Create Flashcard use case.
 * Translates output data from the interactor into a CreateFlashcardState
 * and updates the ViewModel and ViewManagerModel accordingly.
 */
public class CreateFlashcardPresenter implements CreateFlashcardOutputBoundary {
    private final CreateFlashcardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs the presenter with its associated ViewModel and ViewManagerModel.
     *
     * @param viewModel the ViewModel storing UI state for the CreateFlashcard view
     * @param viewManagerModel the global view manager used for navigation
     */
    public CreateFlashcardPresenter(CreateFlashcardViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Updates the CreateFlashcardViewModel with the latest output data
     * and triggers view updates or navigation when needed.
     *
     * @param outputData the data produced by the interactor after saving/deleting a set
     */
    @Override
    public void present(CreateFlashcardOutputData outputData) {
        CreateFlashcardState lastState = viewModel.getState();
        CreateFlashcardState state = setCurrentSet(outputData.getSet());
        state.setMessage(outputData.getMessage());
        viewModel.setState(state);
        if (!Objects.equals(lastState.getSetName(), state.getSetName())) {
            viewManagerModel.firePropertyChange();
        }
        if (outputData.isRedirectHome()) {
            viewManagerModel.setState("generator");
            viewManagerModel.firePropertyChange();
        }
    }

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