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

public class GeneratorView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "generator";

    private final GeneratorViewModel generatorViewModel;

    private GeneratorController generatorController;

    private final JTextField subjectInputField = new JTextField("What do you want to learn today?",
            20);
    private final JLabel errorMessageLabel = new JLabel();

    private final JButton generateButton;

    public GeneratorView(GeneratorViewModel viewModel) {
        this.generatorViewModel = viewModel;
        this.generatorViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("FlashAI");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel inputPanel = new JPanel();
//        inputPanel.add(new JLabel("Subject: "));
        subjectInputField.setBackground(new Color(255, 255, 255, 210));
        inputPanel.add(subjectInputField);
        inputPanel.setBackground(new Color(201, 105, 248));
        generateButton = new JButton("Generate!");
        generateButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(201, 105, 248));
        this.add(inputPanel);
        this.add(errorMessageLabel);
        this.add(generateButton);

        setupListeners();
    }

    private void setupListeners() {
        subjectInputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            private void update() {
                GeneratorState currentState = generatorViewModel.getState();
                currentState.setSubject(subjectInputField.getText());
                generatorViewModel.setState(currentState);
            }

            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });
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
