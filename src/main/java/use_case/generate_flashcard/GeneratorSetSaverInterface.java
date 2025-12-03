package use_case.generate_flashcard;


/**
 * The interface for saving sets in the generator use case.
 */

public interface GeneratorSetSaverInterface {

    int save(String jsonString);
}
