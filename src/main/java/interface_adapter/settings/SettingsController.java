package interface_adapter.settings;

public class SettingsController {
    private final SettingsViewModel viewModel;

    public SettingsController(SettingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void saveSettings(String apiKey, String directory) {
        viewModel.setApiKey(apiKey);
        viewModel.setDirectory(directory);

        System.out.println("Settings saved: " + apiKey + ", " + directory);
}}
