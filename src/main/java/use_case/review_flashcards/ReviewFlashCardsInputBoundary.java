package use_case.review_flashcards;

/**
 * Input Boundary for actions which are related to reviewing flashcards.
 */
public interface ReviewFlashCardsInputBoundary {
    void nextQuestion();

    void previousQuestion();

    void flipCard();

    void editSet();
}
