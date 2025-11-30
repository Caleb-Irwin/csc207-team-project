package use_case.generate_flashcard;

public class GeneratorInputData {

    private final String subject;


    public GeneratorInputData(String subject) {
        this.subject = subject;
    }

    String getSubject() {
        return subject;
    }
}
