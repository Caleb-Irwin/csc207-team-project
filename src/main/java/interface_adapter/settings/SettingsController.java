package interface_adapter.settings;

import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;

/**
 * Controller for handling settings-related user actions.
 */
public class SettingsController {

    private final SettingsInputBoundary interactor;

    /**
     * Creates a SettingsController.
     *
     * @param interactor the use case interactor
     */
    public SettingsController(SettingsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Saves the given API key through the use case.
     *
     * @param apiKey the API key to save
     */
    public void saveSettings(String apiKey) {
        SettingsInputData inputData = new SettingsInputData(apiKey);
        interactor.saveSettings(inputData);
    }
}
