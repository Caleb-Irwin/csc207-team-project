package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.settings.SettingsController;
import interface_adapter.settings.SettingsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SettingsView extends JPanel implements PropertyChangeListener {

    private static final String VIEW_NAME = "settings";

    public String getViewName() {
        return VIEW_NAME;
    }

    private final SettingsViewModel viewModel;
    private SettingsController controller;

    private final JTextField apiKeyField = new JTextField(20);

    public SettingsView(SettingsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Settings");
        add(title, BorderLayout.NORTH);

        // Fields panel
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2));
        fieldsPanel.add(new JLabel("API Key:"));
        fieldsPanel.add(apiKeyField);
        add(fieldsPanel, BorderLayout.CENTER); // <-- This was missing

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (controller != null) {
                controller.saveSettings (apiKeyField.getText());
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize fields from current state
        updateFieldsFromState();
    }

    public void setSettingsController(SettingsController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            updateFieldsFromState();
        }
    }
    private void updateFieldsFromState() {
        apiKeyField.setText(viewModel.getState().getApiKey());
    }

}
