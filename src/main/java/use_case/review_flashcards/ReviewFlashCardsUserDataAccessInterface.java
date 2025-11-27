package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * DAO interface for the Review Flashcards Use Case.
 */
public interface ReviewFlashCardsUserDataAccessInterface {

    /**
     * Returns the flashcard set with the given ID.
     * 
     * @param flashcardSetId the flashcard set ID to look up
     * @return the flashcard set with the given ID
     */
    FlashCardSet getFlashcardSet(String flashcardSetId);

    /**
     * Checks if a flashcard set with the given ID exists.
     * 
     * @param flashcardSetId the flashcard set ID to check
     * @return true if the flashcard set exists; false otherwise
     */
    boolean existsFlashcardSet(String flashcardSetId);

    /**
     * Saves the flashcard set.
     * 
     * @param flashcardSet the flashcard set to save
     */
    void saveFlashcardSet(FlashCardSet flashcardSet);

}
