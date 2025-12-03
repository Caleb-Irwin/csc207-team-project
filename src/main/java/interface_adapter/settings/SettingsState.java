package interface_adapter.settings;

/**
 * Holds the current state of the settings view.
 */
public class SettingsState {
    private String theme;
    private boolean notifications;

    /**
     * @return the selected theme
     */
    public String getTheme() { return theme; }

    /**
     * Set the selected theme.
     *
     * @param t the theme name
     */
    public void setTheme(String t) { theme = t; }

    /**
     * @return whether notifications are enabled
     */
    public boolean getNotifications() { return notifications; }

    /**
     * Set whether notifications are enabled.
     *
     * @param n true to enable notifications
     */
    public void setNotifications(boolean n) { notifications = n; }
}
