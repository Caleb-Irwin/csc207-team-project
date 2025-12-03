package use_case.review_flashcards;

/**
 * Input Boundary for actions which are related to reviewing flashcards.
 */
public interface ReviewFlashCardsInputBoundary {

    /**
     * Advances to the next flashcard in the set.
     */
    void nextQuestion();

    /**
     * Returns to the previous flashcard in the set.
     */
    void previousQuestion();

    /**
     * Flips the current flashcard to show the other side (question/answer).
     */
    void flipCard();

    /**
     * Navigates to the edit view for the current flashcard set.
     */
    void editSet();

    /**
     * Ensures that the currently displayed flashcard set matches the given ID.
     * Updates the view if the set has changed.
     *
     * @param flashCardSetId the ID of the flashcard set to display
     */
    void ensureCorrectSet(int flashCardSetId);
}
