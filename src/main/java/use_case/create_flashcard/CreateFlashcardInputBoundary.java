package use_case.create_flashcard;

import java.util.List;

/**
 * Input boundary for the Create Flashcard use case.
 */
public interface CreateFlashcardInputBoundary {
    void execute(CreateFlashcardInputData inputData);
    void saveFlashcards(String setName, List<String> questions, List<String> answers);
    void deleteSet(String setName);
}

