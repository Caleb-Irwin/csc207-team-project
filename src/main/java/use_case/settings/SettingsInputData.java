package use_case.settings;

/**
 * Data needed to save application settings.
 */
public class SettingsInputData {
    private final String apiKey;

    /**
     * Create a new settings input data object.
     *
     * @param apiKey the API key to save
     */
    public SettingsInputData(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }
}
