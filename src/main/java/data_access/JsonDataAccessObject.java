package data_access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import entity.FlashCardSet;
import use_case.ApiKeyDataAccessInterface;
import use_case.FlashCardSetsDataAccessInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import use_case.settings.SettingsDataAccessInterface;

/**
 * Data Access Object that persists flashcard data to a JSON file.
 * Implements interfaces for API key management, flashcard set operations, and
 * settings.
 * Uses Jackson ObjectMapper for JSON serialization and deserialization.
 */
public class JsonDataAccessObject
        implements ApiKeyDataAccessInterface, FlashCardSetsDataAccessInterface, SettingsDataAccessInterface {
    private final String FILE_NAME = "/data.json";
    private final String FILE_PATH;
    private ObjectMapper mapper = new ObjectMapper();
    private ArrayList<Runnable> callbacks = new ArrayList<>();

    private ArrayList<FlashCardSet> flashCardSets = new ArrayList<>();
    private String apiKey = "";

    /**
     * Constructs a JsonDataAccessObject with the specified folder path.
     * Creates the directory if it doesn't exist and loads existing data from the
     * JSON file.
     *
     * @param folder the folder path where the JSON data file will be stored
     */
    public JsonDataAccessObject(String folder) {
        FILE_PATH = folder + FILE_NAME;
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApiKey() {
        return apiKey;
    }

    /**
     * {@inheritDoc}
     * Saves the API key to persistent storage.
     *
     * @param apiKey the API key to save
     */
    @Override
    public void saveApiKey(String apiKey) {
        this.apiKey = apiKey;
        updateFile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<FlashCardSet> getFlashCardSets() {
        return flashCardSets;
    }

    /**
     * {@inheritDoc}
     * Retrieves a flashcard set by its unique identifier.
     *
     * @param setId the ID of the flashcard set to retrieve
     * @return the FlashCardSet with the given ID, or null if not found
     */
    @Override
    public FlashCardSet getFlashCardSetById(Integer setId) {
        if (setId == null) {
            return null;
        }
        for (FlashCardSet set : flashCardSets) {
            if (setId.equals(set.getId())) {
                return set;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * Creates a new flashcard set and persists it to the JSON file.
     *
     * @param flashCardSet the flashcard set to create
     */
    @Override
    public void createFlashCardSet(FlashCardSet flashCardSet) {
        flashCardSets.add(flashCardSet);
        updateFile();
    }

    /**
     * {@inheritDoc}
     * Updates an existing flashcard set identified by the given ID.
     *
     * @param id           the ID of the flashcard set to update
     * @param flashCardSet the new flashcard set data
     */
    @Override
    public void updateFlashCardSet(Integer id, FlashCardSet flashCardSet) {
        if (id == null) {
            return;
        }
        for (int i = 0; i < flashCardSets.size(); i++) {
            if (id.equals(flashCardSets.get(i).getId())) {
                flashCardSets.set(i, flashCardSet);
                updateFile();
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     * Deletes a flashcard set by its ID and updates the JSON file.
     *
     * @param id the ID of the flashcard set to delete
     */
    @Override
    public void deleteFlashCardSet(Integer id) {
        if (id == null) {
            return;
        }
        for (int i = 0; i < flashCardSets.size(); i++) {
            if (id.equals(flashCardSets.get(i).getId())) {
                flashCardSets.remove(i);
                updateFile();
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     * Calculates and returns the next available ID for a new flashcard set.
     *
     * @return the next available integer ID
     */
    @Override
    public int getNextAvailableId() {
        int maxId = 0;
        for (FlashCardSet set : flashCardSets) {
            Integer id = set.getId();
            if (id != null && id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }

    /**
     * {@inheritDoc}
     * Registers a callback to be executed whenever data is updated.
     *
     * @param callback the Runnable to execute on data updates
     */
    @Override
    public void registerCallBackOnUpdate(Runnable callback) {
        callbacks.add(callback);
    }

    /**
     * Writes the current state to the JSON file and notifies all registered
     * callbacks.
     *
     * @throws RuntimeException if the file cannot be saved
     */
    private void updateFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), new HelperJsonAccessObject(apiKey, flashCardSets));
            for (Runnable callback : callbacks) {
                callback.run();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save set.", e);
        }
    }

    /**
     * Loads data from the JSON file into memory.
     * If the file doesn't exist, creates a new empty file.
     *
     * @throws RuntimeException if the file cannot be loaded
     */
    private void loadFile() {
        try {
            if (!new File(FILE_PATH).exists()) {
                updateFile();
                return;
            }
            HelperJsonAccessObject res = mapper.readValue(new File(FILE_PATH), HelperJsonAccessObject.class);
            this.apiKey = res.getApiKey();
            this.flashCardSets = res.getFlashCardSets();
        } catch (IOException e) {
            throw new RuntimeException("Could not load set.", e);
        }
    }

    /**
     * {@inheritDoc}
     * Saves settings by persisting the API key.
     *
     * @param apiKey the API key to save as part of settings
     */
    @Override
    public void saveSettings(String apiKey) {
        // Reuse existing apiKey field and save logic
        saveApiKey(apiKey); // this already writes to JSON
    }

    /**
     * {@inheritDoc}
     * Loads the API key from persistent storage.
     *
     * @return the stored API key
     */
    @Override
    public String loadApiKey() {
        return getApiKey(); // reuse existing getter
    }
}