package data_access;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import entity.FlashCardSet;

public class HelperJsonAccessObject {
    private final String apiKey;
    private final ArrayList<FlashCardSet> flashCardSets;

    public HelperJsonAccessObject() {
        this("", new ArrayList<>());
    }

    @JsonCreator
    public HelperJsonAccessObject(
            @JsonProperty("apiKey") String apiKey,
            @JsonProperty("flashCardSets") ArrayList<FlashCardSet> flashCardSets) {
        this.apiKey = apiKey;
        this.flashCardSets = flashCardSets;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ArrayList<FlashCardSet> getFlashCardSets() {
        return flashCardSets;
    }
}
