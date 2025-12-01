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
    private final JLabel loadingLabel = new JLabel(); // This is what we'll use for loading
    private final JButton generateButton;


    // private final LoadingPopup loadingPopup; // REMOVED


    private static final Color BACKGROUND_COLOR = new Color(217, 210, 230);
    private static final Color INPUT_FIELD_COLOR = new Color(180, 180, 180);
    private static final Color BUTTON_COLOR = new Color(229, 115, 180);


    public GeneratorView(GeneratorViewModel viewModel) {
        this.generatorViewModel = viewModel;
        this.generatorViewModel.addPropertyChangeListener(this);


        // this.loadingPopup = new LoadingPopup(this); // REMOVED

        final JLabel title = new JLabel("FlashAI");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        loadingLabel.setForeground(Color.BLUE); // Set initial color for loading message


        gbc.gridy = 1;
        this.add(errorMessageLabel, gbc);
        this.add(loadingLabel, gbc); // loadingLabel will now be updated directly


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


        subjectInputField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (subjectInputField.getText().equals(placeholder)) {
                    subjectInputField.setText("");
                    subjectInputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (subjectInputField.getText().isEmpty()) {
                    subjectInputField.setText(placeholder);
                    subjectInputField.setForeground(Color.GRAY);
                }
            }
        });


        subjectInputField.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            GeneratorState state = generatorViewModel.getState();
            String subject = state.getSubject();

            if (subject == null || subject.trim().isEmpty() || subject.equals(placeholder)) {
                errorMessageLabel.setText("Please enter a subject to generate flashcards.");
                loadingLabel.setText(""); // Ensure loading label is not shown on validation error
                return;
            }

            // Clear any previous error message
            errorMessageLabel.setText("");

            // Set loading state and fire property change to update loadingLabel
            state.setLoading(true);
            generatorViewModel.setState(state);
            generatorViewModel.firePropertyChange();

            // Execute the controller
            generatorController.execute(subject);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GeneratorState state = (GeneratorState) evt.getNewValue();

        // Update the error message label
        errorMessageLabel.setText(state.getGeneratorError());

        // Use loadingLabel to show/hide loading status
        if (state.isLoading()) {
            loadingLabel.setText("Generating flashcards... please wait.");
            loadingLabel.setForeground(Color.BLUE); // Set color to make it noticeable
        } else {
            loadingLabel.setText(""); // Clear the loading message when done
        }

        // Clear the input field if no error was reported (implies success)
        if (state.getGeneratorError() == null || state.getGeneratorError().isEmpty()) {
            // Note: This only clears the text field, not the state's subject property
            subjectInputField.setText("");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setGeneratorController(GeneratorController controller) {
        this.generatorController = controller;
    }
}