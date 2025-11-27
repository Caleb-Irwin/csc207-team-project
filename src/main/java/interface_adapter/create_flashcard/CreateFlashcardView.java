package interface_adapter.create_flashcard;

import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CreateFlashcardView extends JPanel {
    private final JTextField setNameField = new JTextField(20);
    private final List<JTextField> questionFields = new ArrayList<>();
    private final List<JTextField> answerFields = new ArrayList<>();
    private final JLabel messageLabel = new JLabel("");
    private final JButton newButton = new JButton("+ New");
    private final JButton saveButton = new JButton("Save");
    private final JButton deleteButton = new JButton("Delete");
    private JPanel formPanel;


    private final CreateFlashcardController controller;

    // Counter to keep track of the question numbers
    private int questionCounter = 1;

    public CreateFlashcardView(CreateFlashcardController controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        formPanel = new JPanel();

        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Set Name:"), gbc);

        gbc.gridx = 1;
        formPanel.add(setNameField, gbc);


        addNewFlashcardPair(formPanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        newButton.addActionListener(e -> addNewFlashcardPair(formPanel));
        saveButton.addActionListener(e -> saveFlashcard());
        deleteButton.addActionListener(e -> {
            String setName = setNameField.getText().trim();
            if (!setName.isEmpty()) {
                controller.deleteSet(setName);
            }
            deleteFlashcards();
        });
    }

    private void addNewFlashcardPair(JPanel formPanel) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField questionField = new JTextField(20);
        JTextField answerField = new JTextField(20);

        questionFields.add(questionField);
        answerFields.add(answerField);

        int row = questionFields.size() * 2 - 1;

        // Question label
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Question " + questionCounter + ":"), gbc);

        // Question field
        gbc.gridx = 1;
        formPanel.add(questionField, gbc);

        // Answer label
        gbc.gridx = 0;
        gbc.gridy = row + 1;
        formPanel.add(new JLabel("Answer:"), gbc);

        // Answer field
        gbc.gridx = 1;
        formPanel.add(answerField, gbc);

        questionCounter++;

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void saveFlashcard() {
        String setName = setNameField.getText();

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        for (int i = 0; i < questionFields.size(); i++) {
            questions.add(questionFields.get(i).getText());
            answers.add(answerFields.get(i).getText());
        }

        controller.saveFlashcards(setName, questions, answers);
        messageLabel.setText("Saved!");
    }

    private void deleteFlashcards() {
        String setName = setNameField.getText();
        controller.deleteSet(setName);

        setNameField.setText("");

        questionFields.clear();
        answerFields.clear();

        formPanel.removeAll();
        questionCounter = 1;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Set Name:"), gbc);

        gbc.gridx = 1;
        formPanel.add(setNameField, gbc);

        addNewFlashcardPair(formPanel);

        formPanel.revalidate();
        formPanel.repaint();

        messageLabel.setText("Set deleted!");
    }
}
