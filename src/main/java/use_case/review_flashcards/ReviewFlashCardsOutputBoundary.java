package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * The output boundary for the Review Flashcards Use Case.
 */
public interface ReviewFlashCardsOutputBoundary {
    void nextCard();

    void previousCard();

    void flipCard();

    void editSet();

    void setFlashCardSet(FlashCardSet flashCardSet);
}
