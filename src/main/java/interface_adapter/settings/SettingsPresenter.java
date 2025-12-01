package interface_adapter.settings;

import use_case.settings.SettingsOutputBoundary;
import use_case.settings.SettingsOutputData;

public class SettingsPresenter implements SettingsOutputBoundary {

    private final SettingsViewModel viewModel;

    public SettingsPresenter(SettingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SettingsOutputData data) {
        viewModel.getState().setApiKey(data.getApiKey());
        viewModel.getState().setDirectory(data.getDirectory());
        viewModel.firePropertyChanged();
    }
}
