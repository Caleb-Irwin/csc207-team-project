package data_access;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import entity.FlashCardSet;

/**
 * A helper data transfer object for JSON serialization and deserialization.
 * This class is used by Jackson to read and write flashcard data to/from JSON
 * files.
 */
public class HelperJsonAccessObject {
    private final String apiKey;
    private final ArrayList<FlashCardSet> flashCardSets;

    /**
     * Default constructor that creates an empty HelperJsonAccessObject.
     * Initializes with an empty API key and an empty list of flashcard sets.
     */
    public HelperJsonAccessObject() {
        this("", new ArrayList<>());
    }

    /**
     * Constructs a HelperJsonAccessObject with the specified API key and flashcard
     * sets.
     * This constructor is used by Jackson for JSON deserialization.
     *
     * @param apiKey        the API key for external services
     * @param flashCardSets the list of flashcard sets to store
     */
    @JsonCreator
    public HelperJsonAccessObject(
            @JsonProperty("apiKey") String apiKey,
            @JsonProperty("flashCardSets") ArrayList<FlashCardSet> flashCardSets) {
        this.apiKey = apiKey;
        this.flashCardSets = flashCardSets;
    }

    /**
     * Returns the stored API key.
     *
     * @return the API key string
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the list of flashcard sets.
     *
     * @return the ArrayList of FlashCardSet objects
     */
    public ArrayList<FlashCardSet> getFlashCardSets() {
        return flashCardSets;
    }
}
