package interface_adapter.settings;

import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;

public class SettingsController {

    private final SettingsInputBoundary interactor;

    public SettingsController(SettingsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void saveSettings(String apiKey) {
        SettingsInputData inputData = new SettingsInputData(apiKey);
        interactor.saveSettings(inputData);
    }
}
