package use_case.create_flashcard;

/**
 * Input boundary for the Create Flashcard use case.
 */
public interface CreateFlashcardInputBoundary {

    void saveFlashcards(CreateFlashcardInputData data);

    void deleteSet();

    void ensureCorrectSet();
}
