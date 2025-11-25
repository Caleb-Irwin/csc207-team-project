package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.settings.*;

import javax.swing.*;
import java.awt.*;
import java.beans.*;

public class SettingsView extends JPanel implements PropertyChangeListener {

    private static final String VIEW_NAME = "settings";

    private final SettingsViewModel viewModel;
    private SettingsController controller = null;

    public SettingsView(SettingsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
//        viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Components:

        // Title:
        JLabel title = new JLabel("Settings");

        // API key:
        JPanel apiPanel = new JPanel();
        apiPanel.add(new JLabel("API Key:"));
        JTextField apiKey = new JTextField();
        apiPanel.add(apiKey);

        // Directory:
        JPanel directoryPanel = new JPanel();
        directoryPanel.add(new JLabel("Directory:"));
        JTextField directory = new  JTextField();
        directoryPanel.add(directory);

        // Buttons:
        JPanel buttonsPanel = new JPanel();
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        buttonsPanel.add(cancel);
        buttonsPanel.add(save);


        // Main Panel:
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(apiPanel);
        this.add(directoryPanel);
        this.add(buttonsPanel);
    }

    public void setSettingsController(SettingsController controller) {
        this.controller = controller;
    }

    public String getViewName() { return VIEW_NAME; };

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
