package use_case.generate_flashcard;

/**
 * Takes in input data for generator.
 */


public class GeneratorInputData {

    private final String subject;


    public GeneratorInputData(String subject) {
        this.subject = subject;
    }

    String getSubject() {
        return subject;
    }
}
