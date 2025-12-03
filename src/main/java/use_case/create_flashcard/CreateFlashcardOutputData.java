package use_case.create_flashcard;

import entity.FlashCardSet;

/**
 * OutputData for the Create Flashcard use case.
 * This class encapsulates the output data that will be passed between the interactor and the presenter.
 * It contains information about the flashcard set, whether the operation was successful,
 * and a message to be displayed in the view. Additionally, it includes a flag to indicate
 * whether the user should be redirected to the home page after the operation.
 */
public class CreateFlashcardOutputData {
    private final Integer setId;
    private final String setName;
    private final boolean success;
    private final String message;
    private final boolean redirectHome;
    private final FlashCardSet set;

    /**
     * Constructs an instance of CreateFlashcardOutputData.
     *
     * @param setId The ID of the flashcard set.
     * @param setName The name of the flashcard set.
     * @param success Indicates if the operation was successful.
     * @param message A message describing the result of the operation.
     * @param redirectHome A flag indicating if the user should be redirected to the home page.
     * @param set The FlashCardSet object that was modified or created.
     */
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

    /**
     * Gets the ID of the flashcard set.
     *
     * @return The ID of the flashcard set.
     */
    public Integer getSetId() {
        return setId;
    }

    /**
     * Gets the flashcard set.
     *
     * @return The flashcard set.
     */
    public FlashCardSet getSet() {
        return set;
    }

    /**
     * Gets the name of the flashcard set.
     *
     * @return The name of the flashcard set.
     */
    public String getSetName() {
        return setName;
    }

    /**
     * Indicates if the operation was successful.
     *
     * @return True if the operation was successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the message describing the result of the operation.
     *
     * @return The message describing the result.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Indicates if the user should be redirected to the home page.
     *
     * @return True if the user should be redirected, false otherwise.
     */
    public boolean isRedirectHome() {
        return redirectHome;
    }
}