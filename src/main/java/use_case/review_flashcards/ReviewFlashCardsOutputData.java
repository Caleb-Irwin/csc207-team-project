package use_case.review_flashcards;

import entity.FlashCardSet;

/**
 * Output Data for the Review Flashcards Use Case.
 */
public class ReviewFlashCardsOutputData {

    private final FlashCardSet flashcardSet;
    private final int currentCardIndex;
    private final boolean showingQuestion;

    public ReviewFlashCardsOutputData(FlashCardSet flashcardSet, int currentCardIndex, boolean showingQuestion) {
        this.flashcardSet = flashcardSet;
        this.currentCardIndex = currentCardIndex;
        this.showingQuestion = showingQuestion;
    }

    public FlashCardSet getFlashcardSet() {
        return flashcardSet;
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    public boolean isShowingQuestion() {
        return showingQuestion;
    }
}
