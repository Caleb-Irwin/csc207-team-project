package use_case.generate_flashcard;

/**
 * Input Boundary for actions which are related to generating a set.
 */

public interface GeneratorInputBoundary {

    /**
     * Executes the generator use case.
     * @param generatorInputData the input data
     */
    void execute(GeneratorInputData generatorInputData);
}
