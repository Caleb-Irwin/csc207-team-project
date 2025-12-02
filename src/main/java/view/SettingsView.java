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

    private static final Color BACKGROUND_COLOR = new Color(217, 210, 230);
    private static final Color INPUT_FIELD_COLOR = new Color(255, 255, 255);
    private static final Color BUTTON_COLOR = new Color(229, 115, 180);
    private static final Color LABEL_COLOR = Color.DARK_GRAY;

    public String getViewName() {
        return VIEW_NAME;
    }

    private final SettingsViewModel viewModel;
    private SettingsController controller;

    private final JTextField apiKeyField = new JTextField(25);

    public SettingsView(SettingsViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel title = new JLabel("Settings");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setForeground(LABEL_COLOR);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // Center panel with fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // API Key label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel apiKeyLabel = new JLabel("API Key:");
        apiKeyLabel.setFont(apiKeyLabel.getFont().deriveFont(Font.BOLD, 14f));
        apiKeyLabel.setForeground(LABEL_COLOR);
        centerPanel.add(apiKeyLabel, gbc);

        // API Key field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        styleTextField(apiKeyField);
        centerPanel.add(apiKeyField, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));

        JButton saveButton = createStyledButton("Save", BUTTON_COLOR);
        saveButton.addActionListener(e -> {
            if (controller != null) {
                controller.saveSettings(apiKeyField.getText());
            }
        });
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize fields from current state
        updateFieldsFromState();

        this.setPreferredSize(new Dimension(1000, 560));
    }

    private void styleTextField(JTextField field) {
        field.setBackground(INPUT_FIELD_COLOR);
        field.setFont(field.getFont().deriveFont(14f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setPreferredSize(new Dimension(300, 35));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(140, 35));
        return button;
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
