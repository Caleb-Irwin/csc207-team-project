package interface_adapter.generate_flashcard;

/**
 * The state for the Generator View Model.
 */

public class GeneratorState {

    private String subject = "";
    private String generatorError;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGeneratorError() {
        return generatorError;
    }

    public void setGeneratorError(String generatorError) {
        this.generatorError = generatorError;
    }

}
