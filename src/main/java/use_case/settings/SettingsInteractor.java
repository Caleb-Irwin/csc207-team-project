package use_case.settings;

public class SettingsInteractor implements SettingsInputBoundary {

    private final SettingsDataAccessInterface settingsDataAccess;
    private final SettingsOutputBoundary presenter;

    public SettingsInteractor(SettingsDataAccessInterface settingsDataAccess,
                              SettingsOutputBoundary presenter) {
        this.settingsDataAccess = settingsDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void saveSettings(SettingsInputData inputData) {
        settingsDataAccess.saveSettings(inputData.getApiKey());

        SettingsOutputData output = new SettingsOutputData(
                inputData.getApiKey()
        );

        presenter.prepareSuccessView(output);
    }
}
