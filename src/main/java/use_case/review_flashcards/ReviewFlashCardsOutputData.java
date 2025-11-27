package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * Output Data for the Review Flashcards Use Case.
 */
public class ReviewFlashCardsOutputData {

    private final FlashCardSet flashcardSet;

    public ReviewFlashCardsOutputData(FlashCardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
    }

    public FlashCardSet getFlashcardSet() {
        return flashcardSet;
    }

}
