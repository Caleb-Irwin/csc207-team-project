package use_case.settings;

public class SettingsOutputData {
    private final String apiKey;

    public SettingsOutputData(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() { return apiKey; }
}
