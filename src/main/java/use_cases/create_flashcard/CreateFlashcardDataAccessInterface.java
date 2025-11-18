package use_cases.create_flashcard;

import entities.FlashCardSet;

/**
 * Gateway interface for accessing and saving FlashCardSet data.
 */
public interface CreateFlashcardDataAccessInterface {


    FlashCardSet load(String setName);

    void saveSet(FlashCardSet set);
}
