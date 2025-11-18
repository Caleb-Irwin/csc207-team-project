package frameworks_and_drivers.flashcard;

import entities.FlashCardSet;
import use_cases.create_flashcard.CreateFlashcardDataAccessInterface;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory implementation of FlashcardSet storage.
 * Used for testing before JSON file storage is ready.
 */
public class InMemoryFlashcardSetDao implements CreateFlashcardDataAccessInterface {

    private final Map<String, FlashCardSet> storage = new HashMap<>();

    @Override
    public FlashCardSet load(String setName) {
        return storage.get(setName);
    }

    @Override
    public void saveSet(FlashCardSet set) {
        storage.put(set.getSetName(), set);
    }
}
