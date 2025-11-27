package use_case.review_flashcards;

/**
 * The output boundary for the Review Flashcards Use Case.
 */
public interface ReviewFlashCardsOutputBoundary {
    /**
     * Prepares the success view for the Review Flashcards Use Case.
     * 
     * @param outputData the output data
     */
    void prepareSuccessView(ReviewFlashCardsOutputData outputData);
}
