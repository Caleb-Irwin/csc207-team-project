package use_case.create_flashcard;

import java.util.List;

/**
 * Input data for saving an entire flashcard set.
 */
public class CreateFlashcardInputData {

    private final String setName;
    private final List<String> questions;
    private final List<String> answers;

    public CreateFlashcardInputData(
            String setName,
            List<String> questions,
            List<String> answers) {
        this.setName = setName;
        this.questions = questions;
        this.answers = answers;
    }

    public String getSetName() {
        return setName;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getAnswers() {
        return answers;
    }
}