package use_case.create_flashcard;

import entity.FlashCardSet;

public class CreateFlashcardOutputData {
    private final Integer setId;
    private final String setName;
    private final boolean success;
    private final String message;
    private final boolean redirectHome;
    private final FlashCardSet set;

    public CreateFlashcardOutputData(Integer setId, String setName,
            boolean success,
            String message, boolean redirectHome, FlashCardSet set) {
        this.setId = setId;
        this.setName = setName;
        this.success = success;
        this.message = message;
        this.redirectHome = redirectHome;
        this.set = set;
    }

    public Integer getSetId() {
        return setId;
    }

    public FlashCardSet getSet() {
        return set;
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

    public boolean isRedirectHome() {
        return redirectHome;
    }
}