package use_case.settings;

public class SettingsInputData {
    private final String apiKey;

    public SettingsInputData(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() { return apiKey;}
}
