package use_case.settings;

public interface SettingsDataAccessInterface {
    void saveSettings(String apiKey, String directory);

    String loadApiKey();
    String loadDirectory();
}
