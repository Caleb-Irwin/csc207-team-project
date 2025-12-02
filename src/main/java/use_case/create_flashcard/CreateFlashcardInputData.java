package use_case.create_flashcard;

import java.util.List;

/**
 * Input data for saving an entire flashcard set.
 * <p>
 * This class holds the data necessary for saving a flashcard set, including the set's name,
 * a list of questions, and a list of answers. It is used as the input for the save operation
 * in the "Create Flashcard" use case.
 */
public class CreateFlashcardInputData {

    private final String setName;
    private final List<String> questions;
    private final List<String> answers;

    /**
     * Constructor to initialize the flashcard set data.
     *
     * @param setName   the name of the flashcard set
     * @param questions the list of questions in the set
     * @param answers   the list of answers corresponding to the questions
     */
    public CreateFlashcardInputData(
            String setName,
            List<String> questions,
            List<String> answers) {
        this.setName = setName;
        this.questions = questions;
        this.answers = answers;
    }

    /**
     * Gets the name of the flashcard set.
     *
     * @return the name of the flashcard set
     */
    public String getSetName() {
        return setName;
    }

    /**
     * Gets the list of questions in the flashcard set.
     *
     * @return the list of questions
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * Gets the list of answers in the flashcard set.
     *
     * @return the list of answers
     */
    public List<String> getAnswers() {
        return answers;
    }
}