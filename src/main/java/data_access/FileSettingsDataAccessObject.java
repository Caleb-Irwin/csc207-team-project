package data_access;

import use_case.settings.SettingsDataAccessInterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSettingsDataAccessObject implements SettingsDataAccessInterface {

    private static final Logger LOGGER = Logger.getLogger(FileSettingsDataAccessObject.class.getName());

    private final String filename;

    public FileSettingsDataAccessObject(String filename) {
        this.filename = filename;
    }

    @Override
    public void saveSettings(String apiKey, String directory) {
        Properties props = new Properties();
        props.setProperty("apiKey", apiKey);
        props.setProperty("directory", directory);
        try (FileOutputStream out = new FileOutputStream(filename)) {
            props.store(out, "Settings");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save settings to " + filename, e);
        }
    }

    @Override
    public String loadApiKey() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(filename)) {
            props.load(in);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load API key from " + filename, e);
            return "";
        }
        return props.getProperty("apiKey", "");
    }

    @Override
    public String loadDirectory() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(filename)) {
            props.load(in);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load directory from " + filename, e);
            return "";
        }
        return props.getProperty("directory", "");
    }
}
