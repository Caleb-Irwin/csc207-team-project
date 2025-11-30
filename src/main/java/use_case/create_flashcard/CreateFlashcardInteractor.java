package use_case.create_flashcard;

import entity.FlashCard;
import entity.FlashCardSet;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardInteractor implements CreateFlashcardInputBoundary {

    private final CreateFlashcardDataAccessInterface dataAccess;

    public CreateFlashcardInteractor(CreateFlashcardDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    /**
     * Handle a single flashcard creation
     */
    @Override
    public void execute(CreateFlashcardInputData inputData) {

        String setName = inputData.getSetName();
        String question = inputData.getQuestions();
        String answer = inputData.getAnswers();

        FlashCardSet set;

        if (dataAccess.existsByName(setName)) {
            set = dataAccess.load(setName);
        } else {
            set = new FlashCardSet(setName, new ArrayList<>(), 0);
        }

        FlashCard card = new FlashCard(question, answer);
        set.addFlashcard(card);

        dataAccess.saveSet(set);
    }

    /**
     * (Save button).
     */
    @Override
    public void saveFlashcards(String setName, List<String> questions, List<String> answers) {

        FlashCardSet set = new FlashCardSet(setName, new ArrayList<>(), 0);

        for (int i = 0; i < questions.size(); i++) {
            FlashCard card = new FlashCard(questions.get(i), answers.get(i));
            set.addFlashcard(card);
        }

        dataAccess.saveSet(set);
    }

    /**
     * Delete a flashcard set.
     */
    @Override
    public void deleteSet(String setName) {
        dataAccess.deleteSet(setName);
    }
}