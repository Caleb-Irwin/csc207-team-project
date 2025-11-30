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
    private final Integer id;

    public FlashCardSet(String setName, List<FlashCard> flashcards) {
        this.setName = setName;
//        this.flashcards = flashcards != null ? flashcards : new ArrayList<>();
        this.flashcards = new ArrayList<>(flashcards);
        this.id = flashcards.size();
    }

    public Integer getId() { return id; }

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
