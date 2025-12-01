package use_case.settings;

public class SettingsInputData {
    private final String apiKey;
    private final String directory;

    public SettingsInputData(String apiKey, String directory) {
        this.apiKey = apiKey;
        this.directory = directory;
    }

    public String getApiKey() { return apiKey;}
    public String getDirectory() { return directory;}
}
