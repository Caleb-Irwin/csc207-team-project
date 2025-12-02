package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * The output boundary for the Review Flashcards Use Case.
 * Defines the contract for presenting flashcard review results to the view.
 */
public interface ReviewFlashCardsOutputBoundary {

    /**
     * Updates the view to display the next flashcard.
     */
    void nextCard();

    /**
     * Updates the view to display the previous flashcard.
     */
    void previousCard();

    /**
     * Updates the view to show the other side of the current flashcard.
     */
    void flipCard();

    /**
     * Transitions the view to edit mode for the current flashcard set.
     */
    void editSet();

    /**
     * Sets the flashcard set to be displayed in the review view.
     *
     * @param flashCardSet the FlashCardSet to display
     */
    void setFlashCardSet(FlashCardSet flashCardSet);
}
