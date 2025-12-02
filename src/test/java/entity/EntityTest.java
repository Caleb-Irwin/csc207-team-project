package entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for entity classes to achieve 100% code coverage
 */
class EntityTest {

    // ===================== FlashCard Tests =====================

    @Test
    void testFlashCardDefaultConstructor() {
        FlashCard card = new FlashCard();
        assertNull(card.getQuestion());
        assertNull(card.getAnswer());
    }

    @Test
    void testFlashCardParameterizedConstructor() {
        FlashCard card = new FlashCard("What is Java?", "A programming language");
        assertEquals("What is Java?", card.getQuestion());
        assertEquals("A programming language", card.getAnswer());
    }

    @Test
    void testFlashCardSetQuestion() {
        FlashCard card = new FlashCard();
        card.setQuestion("New Question");
        assertEquals("New Question", card.getQuestion());
    }

    @Test
    void testFlashCardSetAnswer() {
        FlashCard card = new FlashCard();
        card.setAnswer("New Answer");
        assertEquals("New Answer", card.getAnswer());
    }

    @Test
    void testFlashCardUpdateQuestionAndAnswer() {
        FlashCard card = new FlashCard("Old Q", "Old A");
        card.setQuestion("Updated Q");
        card.setAnswer("Updated A");
        assertEquals("Updated Q", card.getQuestion());
        assertEquals("Updated A", card.getAnswer());
    }

    // ===================== FlashCardSet Tests =====================

    @Test
    void testFlashCardSetDefaultConstructor() {
        FlashCardSet set = new FlashCardSet();
        assertNull(set.getSetName());
        assertNull(set.getId());
        assertNotNull(set.getFlashcards());
        assertTrue(set.getFlashcards().isEmpty());
    }

    @Test
    void testFlashCardSetParameterizedConstructor() {
        List<FlashCard> cards = new ArrayList<>();
        cards.add(new FlashCard("Q1", "A1"));
        FlashCardSet set = new FlashCardSet("My Set", cards, 42);

        assertEquals("My Set", set.getSetName());
        assertEquals(42, set.getId());
        assertEquals(1, set.getFlashcards().size());
        assertEquals("Q1", set.getFlashcards().get(0).getQuestion());
    }

    @Test
    void testFlashCardSetGetId() {
        FlashCardSet set = new FlashCardSet("Test", new ArrayList<>(), 100);
        assertEquals(100, set.getId());
    }

    @Test
    void testFlashCardSetSetId() {
        FlashCardSet set = new FlashCardSet();
        set.setId(55);
        assertEquals(55, set.getId());
    }

    @Test
    void testFlashCardSetGetSetName() {
        FlashCardSet set = new FlashCardSet("Study Cards", new ArrayList<>(), 1);
        assertEquals("Study Cards", set.getSetName());
    }

    @Test
    void testFlashCardSetSetSetName() {
        FlashCardSet set = new FlashCardSet();
        set.setSetName("New Name");
        assertEquals("New Name", set.getSetName());
    }

    @Test
    void testFlashCardSetGetFlashcards() {
        List<FlashCard> cards = new ArrayList<>();
        cards.add(new FlashCard("Q1", "A1"));
        cards.add(new FlashCard("Q2", "A2"));
        FlashCardSet set = new FlashCardSet("Test Set", cards, 1);

        List<FlashCard> retrieved = set.getFlashcards();
        assertEquals(2, retrieved.size());
    }

    @Test
    void testFlashCardSetSetFlashcards() {
        FlashCardSet set = new FlashCardSet();
        List<FlashCard> newCards = new ArrayList<>();
        newCards.add(new FlashCard("New Q", "New A"));
        set.setFlashcards(newCards);

        assertEquals(1, set.getFlashcards().size());
        assertEquals("New Q", set.getFlashcards().get(0).getQuestion());
    }

    @Test
    void testFlashCardSetAddFlashcard() {
        FlashCardSet set = new FlashCardSet();
        assertTrue(set.getFlashcards().isEmpty());

        set.addFlashcard(new FlashCard("Added Q", "Added A"));
        assertEquals(1, set.getFlashcards().size());
        assertEquals("Added Q", set.getFlashcards().get(0).getQuestion());
        assertEquals("Added A", set.getFlashcards().get(0).getAnswer());
    }

    @Test
    void testFlashCardSetAddMultipleFlashcards() {
        FlashCardSet set = new FlashCardSet("Multi", new ArrayList<>(), 5);

        set.addFlashcard(new FlashCard("Q1", "A1"));
        set.addFlashcard(new FlashCard("Q2", "A2"));
        set.addFlashcard(new FlashCard("Q3", "A3"));

        assertEquals(3, set.getFlashcards().size());
        assertEquals("Q1", set.getFlashcards().get(0).getQuestion());
        assertEquals("Q2", set.getFlashcards().get(1).getQuestion());
        assertEquals("Q3", set.getFlashcards().get(2).getQuestion());
    }

    @Test
    void testFlashCardSetWithNullId() {
        FlashCardSet set = new FlashCardSet("Null ID Set", new ArrayList<>(), null);
        assertNull(set.getId());
    }

    @Test
    void testFlashCardSetWithEmptyName() {
        FlashCardSet set = new FlashCardSet("", new ArrayList<>(), 1);
        assertEquals("", set.getSetName());
    }
}
