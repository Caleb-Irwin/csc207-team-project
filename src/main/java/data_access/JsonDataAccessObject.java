package data_access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import entity.FlashCardSet;
import use_case.ApiKeyDataAccessInterface;
import use_case.FlashCardSetsDataAccessInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import use_case.settings.SettingsDataAccessInterface;

public class JsonDataAccessObject implements ApiKeyDataAccessInterface, FlashCardSetsDataAccessInterface, SettingsDataAccessInterface {
    private final String FILE_NAME = "/data.json";
    private final String FILE_PATH;
    private ObjectMapper mapper = new ObjectMapper();
    private ArrayList<Runnable> callbacks = new ArrayList<>();

    private ArrayList<FlashCardSet> flashCardSets = new ArrayList<>();
    private String apiKey = "";

    public JsonDataAccessObject(String folder) {
        FILE_PATH = folder + FILE_NAME;
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadFile();
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void saveApiKey(String apiKey) {
        this.apiKey = apiKey;
        updateFile();
    }

    @Override
    public ArrayList<FlashCardSet> getFlashCardSets() {
        return flashCardSets;
    }

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

    @Override
    public void createFlashCardSet(FlashCardSet flashCardSet) {
        flashCardSets.add(flashCardSet);
        updateFile();
    }

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

    @Override
    public void registerCallBackOnUpdate(Runnable callback) {
        callbacks.add(callback);
    }

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
    @Override
    public void saveSettings(String apiKey) {
        // Reuse existing apiKey field and save logic
        saveApiKey(apiKey); // this already writes to JSON
    }

    @Override
    public String loadApiKey() {
        return getApiKey(); // reuse existing getter
    }
}