package use_case.create_flashcard;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import use_case.FlashCardSetsDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the Create Flashcard use case.
 * This class handles the business logic for creating, saving, deleting, and ensuring the correct flashcard set.
 * It communicates with the data access layer to retrieve, update, and delete flashcard sets.
 * It also interacts with the presenter to pass information about the current operation to the view.
 */
public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {
    private final FlashCardSetsDataAccessInterface dataAccess;
    private final CreateFlashcardOutputBoundary presenter;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a CreateFlashcardInteractor instance.
     *
     * @param dataAccess The data access interface to interact with the flashcard sets in the database.
     * @param presenter  The presenter to communicate the results to the view.
     * @param viewManagerModel The model for managing view state and navigation.
     */
    public CreateFlashcardInteractor(FlashCardSetsDataAccessInterface dataAccess,
                                     CreateFlashcardOutputBoundary presenter,
                                     ViewManagerModel viewManagerModel) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Saves the flashcards in the current flashcard set.
     * This method updates the flashcard set's name and its flashcards, then saves the updated set in the data access layer.
     *
     * @param inputData The input data containing the set name, questions, and answers.
     */
    @Override
    public void saveFlashcards(CreateFlashcardInputData inputData) {
        String setName = inputData.getSetName();
        FlashCardSet set = getCurrentFlashCardSet();
        Integer id = set.getId();
        List<String> questions = inputData.getQuestions();
        List<String> answers = inputData.getAnswers();
        ArrayList<FlashCard> flashcards = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            flashcards.add(new FlashCard(questions.get(i), answers.get(i)));
        }
        set.setSetName(setName);
        set.setFlashcards(flashcards);
        dataAccess.updateFlashCardSet(id, set);
        presenter.present(new CreateFlashcardOutputData(
                id,
                setName,
                true,
                "Set saved!",
                false,
                set));
    }

    /**
     * Deletes the current flashcard set.
     * This method deletes the flashcard set from the data access layer if it exists.
     */
    @Override
    public void deleteSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        boolean existed = dataAccess.getFlashCardSetById(currentFlashCardSetId) != null;
        if (existed) {
            dataAccess.deleteFlashCardSet(currentFlashCardSetId);
        }
        presenter.present(new CreateFlashcardOutputData(
                currentFlashCardSetId,
                null,
                true,
                existed ? "Set deleted!" : "Set does not exist.", existed, getCurrentFlashCardSet()));
    }

    /**
     * Ensures the current flashcard set is correctly loaded and updates the view with its details.
     * This method retrieves the current flashcard set from the data access layer and passes the data to the presenter.
     */
    @Override
    public void ensureCorrectSet() {
        FlashCardSet currentSet = getCurrentFlashCardSet();
        Integer id = currentSet != null ? currentSet.getId() : null;
        presenter.present(new CreateFlashcardOutputData(
                id,
                currentSet != null ? currentSet.getSetName() : "",
                false,
                "",
                false,
                currentSet));
    }

    /**
     * Retrieves the current flashcard set using the view manager model.
     *
     * @return The current flashcard set.
     */
    private FlashCardSet getCurrentFlashCardSet() {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        return dataAccess.getFlashCardSetById(currentFlashCardSetId);
    }
}