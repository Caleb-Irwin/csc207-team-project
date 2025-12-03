package interface_adapter.settings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for settings, providing observable state for the UI.
 */
public class SettingsViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final SettingsState state = new SettingsState();

    /**
     * @return the current settings state
     */
    public SettingsState getState() {
        return state;
    }

    /**
     * Notify listeners that the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    /**
     * Add a listener to observe state changes.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Holds the observable settings data.
     */
    public static class SettingsState {
        private String apiKey = "";
        private String directory = "";

        /**
         * @return the API key
         */
        public String getApiKey() { return apiKey; }

        /**
         * @return the directory path
         */
        public String getDirectory() { return directory; }

        /**
         * Set the API key.
         *
         * @param apiKey the new API key
         */
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }

        /**
         * Set the directory path.
         *
         * @param directory the new directory
         */
        public void setDirectory(String directory) { this.directory = directory; }
    }
}
