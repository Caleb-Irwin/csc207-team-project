package interface_adapter.generate_flashcard;

import use_case.generate_flashcard.GeneratorInputBoundary;
import use_case.generate_flashcard.GeneratorInputData;


/**
 * The controller for the generator use_case
 */

public class GeneratorController {

    private final GeneratorInputBoundary generatorUseCaseInteractor;


    public GeneratorController(GeneratorInputBoundary generatorInputBoundary) {
        this.generatorUseCaseInteractor = generatorInputBoundary;
    }

    public void execute(String subject){
        final GeneratorInputData generatorInputData = new GeneratorInputData(subject);
        generatorUseCaseInteractor.execute(generatorInputData);

    }
}
