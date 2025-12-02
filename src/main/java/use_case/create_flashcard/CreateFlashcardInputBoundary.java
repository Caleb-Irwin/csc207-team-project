package use_case.create_flashcard;

/**
 * Input boundary for the Create Flashcard use case.
 */
public interface CreateFlashcardInputBoundary {

    /**
     * Saves the flashcards based on the provided input data.
     *
     * @param data the input data containing the set name, questions, and answers
     */
    void saveFlashcards(CreateFlashcardInputData data);

    /**
     * Deletes the current flashcard set.
     */
    void deleteSet();

    /**
     * Ensures that the correct flashcard set is being used by checking the current state.
     */
    void ensureCorrectSet();
}