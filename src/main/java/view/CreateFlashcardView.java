package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardState;
import interface_adapter.create_flashcard.CreateFlashcardViewModel;
import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsState;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardView extends JPanel implements PropertyChangeListener {

    private final JTextField setIdField = new JTextField(10);
    private final JTextField setNameField = new JTextField(20);

    private final List<JTextField> questionFields = new ArrayList<>();
    private final List<JTextField> answerFields = new ArrayList<>();

    private final JLabel messageLabel = new JLabel("");

    private final JButton newButton = new JButton("+ New");
    private final JButton saveButton = new JButton("Save");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton startReviewButton = new JButton("Start Review");

    private JPanel formPanel;
    private int questionCounter = 1;

    private final CreateFlashcardController controller;
    private final CreateFlashcardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final ReviewFlashCardsController reviewController;
    private final ReviewFlashCardsViewModel reviewViewModel;

    public CreateFlashcardView(CreateFlashcardViewModel viewModel,
                               CreateFlashcardController controller,
                               ViewManagerModel viewManagerModel,
                               ReviewFlashCardsController reviewController,
                               ReviewFlashCardsViewModel reviewViewModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        this.reviewController = reviewController;
        this.reviewViewModel = reviewViewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        formPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        buildInitialForm();

        // Buttons section
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(startReviewButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        // Button listeners
        newButton.addActionListener(e -> addNewFlashcardPair());
        saveButton.addActionListener(e -> saveFlashcard());
        deleteButton.addActionListener(e -> deleteFlashcards());
        startReviewButton.addActionListener(e -> startReview());
    }

    /** ------------------- FORM BUILDING ------------------- **/

    private void buildInitialForm() {
        formPanel.removeAll();
        questionFields.clear();
        answerFields.clear();
        questionCounter = 1;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Row 0: Set ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Set ID:"), gbc);

        gbc.gridx = 1;
        formPanel.add(setIdField, gbc);

        // Row 1: Set Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Set Name:"), gbc);

        gbc.gridx = 1;
        formPanel.add(setNameField, gbc);

        addNewFlashcardPair();
        formPanel.revalidate();
        formPanel.repaint();
    }

    private void addNewFlashcardPair() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int baseRow = 2 + (questionFields.size() * 2);

        JTextField questionField = new JTextField(20);
        JTextField answerField = new JTextField(20);

        questionFields.add(questionField);
        answerFields.add(answerField);

        // Question label
        gbc.gridx = 0;
        gbc.gridy = baseRow;
        formPanel.add(new JLabel("Question " + questionCounter + ":"), gbc);

        // Question input
        gbc.gridx = 1;
        formPanel.add(questionField, gbc);

        // Answer label
        gbc.gridx = 0;
        gbc.gridy = baseRow + 1;
        formPanel.add(new JLabel("Answer:"), gbc);

        // Answer input
        gbc.gridx = 1;
        formPanel.add(answerField, gbc);

        questionCounter++;

        formPanel.revalidate();
        formPanel.repaint();
    }

    /** ------------------- SAVE LOGIC ------------------- **/

    private void saveFlashcard() {
        try {
            Integer setId = Integer.parseInt(setIdField.getText().trim());
            String setName = setNameField.getText().trim();

            List<String> questions = new ArrayList<>();
            List<String> answers = new ArrayList<>();

            for (int i = 0; i < questionFields.size(); i++) {
                questions.add(questionFields.get(i).getText());
                answers.add(answerFields.get(i).getText());
            }

            controller.saveFlashcards(setId, setName, questions, answers);

        } catch (NumberFormatException e) {
            messageLabel.setText("Error: Set ID must be a number");
        }
    }

    /** ------------------- DELETE LOGIC ------------------- **/

    private void deleteFlashcards() {
        try {
            Integer setId = Integer.parseInt(setIdField.getText().trim());
            controller.deleteSet(setId);
            buildInitialForm();
        } catch (NumberFormatException e) {
            messageLabel.setText("Error: Set ID must be a number");
        }
    }
    private void startReview() {
        try {
            int setId = Integer.parseInt(setIdField.getText().trim());

            // 1. Set the selected set for Review view
            viewManagerModel.setCurrentFlashCardSetId(setId);
            // 2. Initialize review state by calling the use case
            reviewViewModel.setState(new ReviewFlashCardsState());
            reviewViewModel.firePropertyChange();
            // 3. Navigate to ReviewFlashCardsView
            viewManagerModel.setState("review flashcards");
            viewManagerModel.firePropertyChange();

        } catch (NumberFormatException e) {
            messageLabel.setText("Error: Set ID must be a number");
        }
    }

        /** ------------------- VIEWMODEL UPDATE ------------------- **/

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!CreateFlashcardViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) return;

        CreateFlashcardState state = (CreateFlashcardState) evt.getNewValue();
        messageLabel.setText(state.getMessage());
    }

    public String getViewName() {
        return "create flashcard";
    }
}