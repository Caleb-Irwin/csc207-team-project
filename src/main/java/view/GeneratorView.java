package view;

import interface_adapter.generate_flashcard.GeneratorController;
import interface_adapter.generate_flashcard.GeneratorState;
import interface_adapter.generate_flashcard.GeneratorViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class GeneratorView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "generator";
    private final String placeholder = "What do you want to review today?";

    private final GeneratorViewModel generatorViewModel;

    private GeneratorController generatorController;


    private final JTextField subjectInputField = new JTextField(30);


    private final JLabel errorMessageLabel = new JLabel();

    private final JButton generateButton;


    private static final Color BACKGROUND_COLOR = new Color(217, 210, 230);
    private static final Color INPUT_FIELD_COLOR = new Color(180, 180, 180);
    private static final Color BUTTON_COLOR = new Color(229, 115, 180);

    public GeneratorView(GeneratorViewModel viewModel) {
        this.generatorViewModel = viewModel;
        this.generatorViewModel.addPropertyChangeListener(this);


        this.setLayout(new GridBagLayout());
        this.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        subjectInputField.setText(placeholder);
        subjectInputField.setForeground(Color.LIGHT_GRAY);
        subjectInputField.setBackground(INPUT_FIELD_COLOR);
        subjectInputField.setPreferredSize(new Dimension(300, 40));
        subjectInputField.setHorizontalAlignment(JTextField.CENTER);
        subjectInputField.setBorder(BorderFactory.createEmptyBorder(
                5, 10, 5, 10));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        this.add(subjectInputField, gbc);


        errorMessageLabel.setForeground(Color.RED);


        gbc.gridy = 1;
        this.add(errorMessageLabel, gbc);


        generateButton = new JButton("Generate!");
        generateButton.addActionListener(this);


        generateButton.setBackground(BUTTON_COLOR);
        generateButton.setForeground(Color.BLACK);
        generateButton.setFont(generateButton.getFont().deriveFont(Font.BOLD, 14f));
        generateButton.setFocusPainted(false);
        generateButton.setBorderPainted(false);
        generateButton.setOpaque(true);
        generateButton.setPreferredSize(new Dimension(120, 35));

        gbc.gridy = 2;
        this.add(generateButton, gbc);


        this.setPreferredSize(new Dimension(1000, 560));
        this.setFocusable(true);
        this.requestFocusInWindow();

        setupListeners();
    }

    private void setupListeners() {
        subjectInputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            private void update() {
                GeneratorState currentState = generatorViewModel.getState();
                currentState.setSubject(subjectInputField.getText());
                if (Objects.equals(subjectInputField.getText(), placeholder)) {
                    currentState.setSubject("");
                }
                generatorViewModel.setState(currentState);
            }

            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });

        // Focus listener for placeholder behavior
        subjectInputField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (subjectInputField.getText().equals(placeholder)) {
                    subjectInputField.setText("");
                    subjectInputField.setForeground(Color.BLACK); // optional: set text color to normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (subjectInputField.getText().isEmpty()) {
                    subjectInputField.setText(placeholder);
                    subjectInputField.setForeground(Color.GRAY); // optional: placeholder in gray
                }
            }
        });

        // Optional: initialize placeholder color
        subjectInputField.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            GeneratorState state = generatorViewModel.getState();
            generatorController.execute(state.getSubject());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GeneratorState state = (GeneratorState) evt.getNewValue();
        errorMessageLabel.setText(state.getGeneratorError());
    }

    public String getViewName() {
        return viewName;
    }

    public void setGeneratorController(GeneratorController controller) {
        this.generatorController = controller;
    }
}
