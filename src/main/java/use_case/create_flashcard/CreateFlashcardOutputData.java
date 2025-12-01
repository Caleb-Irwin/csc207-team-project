package use_case.create_flashcard;

public class CreateFlashcardOutputData {
    private final Integer setId;
    private final String setName;
    private final boolean success;
    private final String message;

    public CreateFlashcardOutputData(Integer setId,String setName,
                                     boolean success,
                                     String message) {
        this.setId = setId;
        this.setName = setName;
        this.success = success;
        this.message = message;
    }
    public Integer getSetId() {
        return setId;
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