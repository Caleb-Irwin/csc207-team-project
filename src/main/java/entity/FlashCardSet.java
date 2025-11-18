package entity;

import java.util.List;

/**
 * Represents a collection of flashcards (a set).
 * Contains multiple flashcards related to a specific topic.
 */

public class FlashCardSet {
    private String setName;
    private List<Flashcard> flashcards;

    public FlashCardSet(String setName, List<Flashcard> flashcards) {
        this.setName = setName;
        this.flashcards = flashcards;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

}
