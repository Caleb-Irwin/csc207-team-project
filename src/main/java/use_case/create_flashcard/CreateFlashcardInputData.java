package use_case.create_flashcard;

import java.util.List;

/**
 * Input data for saving an entire flashcard set.
 */
public class CreateFlashcardInputData {

    private final Integer setId;
    private final String setName;
    private final List<String> questions;
    private final List<String> answers;

    public CreateFlashcardInputData(Integer setId,
                                    String setName,
                                    List<String> questions,
                                    List<String> answers) {
        this.setId = setId;
        this.setName = setName;
        this.questions = questions;
        this.answers = answers;
    }

    public Integer getSetId() { return setId; }

    public String getSetName() { return setName; }

    public List<String> getQuestions() { return questions; }

    public List<String> getAnswers() { return answers; }
}