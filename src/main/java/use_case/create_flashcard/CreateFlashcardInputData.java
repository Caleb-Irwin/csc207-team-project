package use_case.create_flashcard;

/**
 * Input data for creating a new flashcard
 */
public class CreateFlashcardInputData {
    private final String questions;
    private final String answers;
    private final String setName;

    public CreateFlashcardInputData(String questions, String answers, String setName) {
        this.questions = questions;
        this.answers = answers;
        this.setName = setName;
    }

    public String getQuestions() {
        return questions;
    }

    public String getAnswers() {
        return answers;
    }

    public String getSetName() {
        return setName;
    }
}
