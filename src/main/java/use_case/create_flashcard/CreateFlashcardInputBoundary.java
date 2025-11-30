package use_case.create_flashcard;

import java.util.List;

/**
 * Input boundary for the Create Flashcard use case.
 */
public interface CreateFlashcardInputBoundary {

    void save(CreateFlashcardInputData inputData);
    void delete(String setName);
}

