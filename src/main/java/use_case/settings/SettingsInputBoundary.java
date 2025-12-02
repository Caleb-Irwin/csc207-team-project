package use_case.settings;

/**
 * Input boundary for saving application settings.
 */
public interface SettingsInputBoundary {

    /**
     * Saves settings based on the provided input data.
     *
     * @param inputData the settings data to save
     */
    void saveSettings(SettingsInputData inputData);
}
