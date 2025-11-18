package use_case.create_flashcard;

import entity.FlashCardSet;

/**
 * Gateway interface for accessing and saving FlashCardSet data.
 */
public interface CreateFlashcardDataAccessInterface {

    FlashCardSet load(String setName);

    void saveSet(FlashCardSet set);
}
