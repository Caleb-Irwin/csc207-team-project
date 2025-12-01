package data_access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import entity.FlashCardSet;
import use_case.ApiKeyDataAccessInterface;
import use_case.FlashCardSetsDataAccessInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataAccessObject implements ApiKeyDataAccessInterface, FlashCardSetsDataAccessInterface {
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
        for (FlashCardSet set : flashCardSets) {
            if (set.getId().equals(setId)) {
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
        for (int i = 0; i < flashCardSets.size(); i++) {
            if (flashCardSets.get(i).getId().equals(id)) {
                flashCardSets.set(i, flashCardSet);
                updateFile();
                return;
            }
        }
    }

    @Override
    public void deleteFlashCardSet(Integer id) {
        for (int i = 0; i < flashCardSets.size(); i++) {
            if (flashCardSets.get(i).getId().equals(id)) {
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
            if (set.getId() > maxId) {
                maxId = set.getId();
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
}