package use_cases.create_flashcard;

/**
 * Output data for the Create Flashcard use case.
 */
public class CreateFlashcardOutputData {

    private final String setName;
    private final String question;
    private final String answer;
    private final boolean success;
    private final String message;


    public CreateFlashcardOutputData(String setName,
                                     String question,
                                     String answer,
                                     boolean success,
                                     String message

    ) {
        this.setName = setName;
        this.question = question;
        this.answer = answer;
        this.success = success;
        this.message = message;


    }

    public String getSetName() {
        return setName;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
