package interface_adapter.settings;

public class SettingsState {
    private String theme;
    private boolean notifications;

    public String getTheme() { return theme; }
    public void setTheme(String t) { theme = t; }

    public boolean getNotifications() { return notifications; }
    public void setNotifications(boolean n) { notifications = n; }
}
