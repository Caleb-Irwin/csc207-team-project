package use_case.settings;

/**
 * Interactor for saving application settings.
 */
public class SettingsInteractor implements SettingsInputBoundary {

    private final SettingsDataAccessInterface settingsDataAccess;
    private final SettingsOutputBoundary presenter;

    /**
     * Creates a new SettingsInteractor.
     *
     * @param settingsDataAccess data access for storing settings
     * @param presenter presenter for preparing the output view
     */
    public SettingsInteractor(SettingsDataAccessInterface settingsDataAccess,
                              SettingsOutputBoundary presenter) {
        this.settingsDataAccess = settingsDataAccess;
        this.presenter = presenter;
    }

    /**
     * Saves the settings and notifies the presenter.
     *
     * @param inputData settings data to save
     */
    @Override
    public void saveSettings(SettingsInputData inputData) {
        settingsDataAccess.saveSettings(inputData.getApiKey());

        SettingsOutputData output = new SettingsOutputData(
                inputData.getApiKey()
        );

        presenter.prepareSuccessView(output);
    }
}
