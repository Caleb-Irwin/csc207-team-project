package interface_adapter.settings;
import java.beans.*;

public class SettingsViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String apiKey = "";
    public String directory = "";

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setApiKey(String newKey) {
        String old = this.apiKey;
        this.apiKey = newKey;
        support.firePropertyChange("apiKey", old, newKey);
    }

    public void setDirectory(String newDir) {
        String old = this.directory;
        this.directory = newDir;
        support.firePropertyChange("directory", old, newDir);
    }
}
