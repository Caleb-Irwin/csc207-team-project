package use_case.review_flashcards;

/**
 * Input Boundary for actions which are related to reviewing flashcards.
 */
public interface ReviewFlashCardsInputBoundary {

    /**
     * Executes the review flashcards use case.
     * 
     * @param reviewFlashCardsInputData the input data
     */
    void execute(ReviewFlashCardsInputData reviewFlashCardsInputData);
}
