package interface_adapter.create_flashcard;

import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardState {

    private String setName = "";
    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private String message = "";

    public CreateFlashcardState() {
        // 2 Usages
    }


    public String getSetName() { return setName; }
    public void setSetName(String setName) { this.setName = setName; }

    public List<String> getQuestions() { return questions; }
    public void setQuestions(List<String> questions) { this.questions = questions; }

    public List<String> getAnswers() { return answers; }
    public void setAnswers(List<String> answers) { this.answers = answers; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}