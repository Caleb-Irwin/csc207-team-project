package interface_adapter.create_flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * State for Create Flashcard use case.
 * <p>
 * This class holds the state of the flashcard set being created. It includes the set name,
 * the list of questions and answers, and any messages to be displayed to the user.
 */
public class CreateFlashcardState {

    private String setName = "";
    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private String message = "";

    /**
     * Default constructor for CreateFlashcardState.
     * Initializes an empty state for creating flashcards.
     */
    public CreateFlashcardState() {}

    /**
     * Gets the name of the flashcard set.
     *
     * @return the name of the flashcard set
     */
    public String getSetName() {
        return setName;
    }

    /**
     * Sets the name of the flashcard set.
     *
     * @param setName the name of the flashcard set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    /**
     * Gets the list of questions in the flashcard set.
     *
     * @return a list of questions
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions for the flashcard set.
     *
     * @param questions a list of questions
     */
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    /**
     * Gets the list of answers for the flashcard set.
     *
     * @return a list of answers
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Sets the list of answers for the flashcard set.
     *
     * @param answers a list of answers
     */
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * Gets the message associated with the current state.
     *
     * @return the message to be displayed
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the current state.
     *
     * @param message the message to be displayed
     */
    public void setMessage(String message) {
        this.message = message;
    }
}