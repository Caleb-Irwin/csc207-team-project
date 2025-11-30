package use_case.create_flashcard;

public class CreateFlashcardOutputData {

    private final String setName;
    private final boolean success;
    private final String message;

    public CreateFlashcardOutputData(String setName,
                                     boolean success,
                                     String message) {
        this.setName = setName;
        this.success = success;
        this.message = message;
    }

    public String getSetName() {
        return setName;
    }
        public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}