package use_case.generate_flashcard;

/**
 * Output Boundary for actions which are related to generating a set.
 */

public interface GeneratorOutputBoundary {

    /**
     * Prepares the success view for the generate_flashcard Use Case.
     *
     */
    void prepareSuccessView(int setID);


    /**
     * Prepares the failure view for the generate_flashcard Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
