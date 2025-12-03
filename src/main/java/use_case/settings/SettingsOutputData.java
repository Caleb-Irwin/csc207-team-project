package use_case.settings;

/**
 * Output data returned after saving settings.
 */
public class SettingsOutputData {
    private final String apiKey;

    /**
     * Creates output data for saved settings.
     *
     * @param apiKey the saved API key
     */
    public SettingsOutputData(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the saved API key
     */
    public String getApiKey() {
        return apiKey;
    }
}
