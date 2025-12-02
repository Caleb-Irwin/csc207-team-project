package use_case.settings;

public interface SettingsDataAccessInterface {
    void saveSettings(String apiKey);

    String loadApiKey();
}
