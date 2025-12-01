package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of flashcards (a set).
 * Contains multiple flashcards related to a specific topic.
 */

public class FlashCardSet {
    private String setName;
    private List<FlashCard> flashcards;
    private Integer id;

    public FlashCardSet() {
        this.flashcards = new ArrayList<>();
    }

    public FlashCardSet(String setName, List<FlashCard> flashcards, Integer id) {
        this.setName = setName;
        this.flashcards = new ArrayList<>();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {   // ← Jackson requires setter
        this.id = id;
    }

        public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public List<FlashCard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<FlashCard> flashcards) {
        this.flashcards = flashcards;
    }

    public void addFlashcard(FlashCard flashcard) {
        this.flashcards.add(flashcard);
    }

}