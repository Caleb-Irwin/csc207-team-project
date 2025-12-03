package use_case.settings;

/**
 * Handles storing and retrieving the user's API key.
 */
public interface SettingsDataAccessInterface {

    /**
     * Save the given API key.
     *
     * @param apiKey the API key to store
     */
    void saveSettings(String apiKey);

    /**
     * Load the stored API key.
     *
     * @return the saved API key, or null if none exists
     */
    String loadApiKey();
}
