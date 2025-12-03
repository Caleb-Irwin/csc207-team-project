package interface_adapter.settings;

import use_case.settings.SettingsOutputBoundary;
import use_case.settings.SettingsOutputData;

/**
 * Presenter that updates the settings view model after a successful save.
 */
public class SettingsPresenter implements SettingsOutputBoundary {

    private final SettingsViewModel viewModel;

    /**
     * Creates a SettingsPresenter.
     *
     * @param viewModel the view model to update
     */
    public SettingsPresenter(SettingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the view model with the saved API key.
     *
     * @param data the output data containing the saved API key
     */
    @Override
    public void prepareSuccessView(SettingsOutputData data) {
        viewModel.getState().setApiKey(data.getApiKey());
        viewModel.firePropertyChanged();
    }
}
