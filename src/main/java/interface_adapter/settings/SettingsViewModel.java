package interface_adapter.settings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SettingsViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private final SettingsState state = new SettingsState();

    public SettingsState getState() {
        return state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public static class SettingsState {
        private String apiKey = "";
        private String directory = "";

        public String getApiKey() { return apiKey; }
        public String getDirectory() { return directory; }

        public void setApiKey(String apiKey) { this.apiKey = apiKey; }
        public void setDirectory(String directory) { this.directory = directory; }
    }
}



