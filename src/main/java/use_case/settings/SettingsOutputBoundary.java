package use_case.settings;

/**
 * Output boundary for presenting saved settings.
 */
public interface SettingsOutputBoundary {

    /**
     * Present the successful save result.
     *
     * @param data the settings output data
     */
    void prepareSuccessView(SettingsOutputData data);
}
