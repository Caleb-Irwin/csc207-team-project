package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_flashcard.CreateFlashcardController;
import interface_adapter.create_flashcard.CreateFlashcardState;
import interface_adapter.create_flashcard.CreateFlashcardViewModel;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * View for the Create Flashcard use case.
 * This class represents the graphical user interface (GUI) for creating and managing flashcard sets.
 * It allows users to create new flashcard sets, add flashcards to a set, save the flashcard set,
 * and delete or modify existing flashcard sets.
 */
public class CreateFlashcardView extends JPanel implements PropertyChangeListener {
    private static final Color BACKGROUND_COLOR = new Color(217, 210, 230);
    private static final Color INPUT_FIELD_COLOR = new Color(255, 255, 255);
    private static final Color BUTTON_COLOR = new Color(229, 115, 180);
    private static final Color NAV_BUTTON_COLOR = new Color(150, 130, 170);
    private static final Color LABEL_COLOR = Color.DARK_GRAY;

    private final JTextField setNameField = new JTextField(25);
    private final List<JTextField> questionFields = new ArrayList<>();
    private final List<JTextField> answerFields = new ArrayList<>();
    private final JLabel messageLabel = new JLabel("");
    private final JPanel formPanel;
    private int questionCounter = 1;

    private final CreateFlashcardController controller;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructor to initialize the CreateFlashcardView.
     *
     * @param viewModel The view model that holds the current state of the flashcard set.
     * @param controller The controller that handles the actions for this view.
     * @param viewManagerModel The view manager that handles navigation and view state.
     */
    public CreateFlashcardView(CreateFlashcardViewModel viewModel,
                               CreateFlashcardController controller,
                               ViewManagerModel viewManagerModel) {
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        viewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel titleLabel = new JLabel("Modify Flashcard Set");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setForeground(LABEL_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.ITALIC, 12f));
        messageLabel.setForeground(new Color(100, 100, 100));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        headerPanel.add(messageLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        buildInitialForm();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton newButton = createStyledButton("+ New Card", NAV_BUTTON_COLOR);
        JButton saveButton = createStyledButton("Save", BUTTON_COLOR);
        JButton deleteButton = createStyledButton("Delete Set", NAV_BUTTON_COLOR);
        JButton startReviewButton = createStyledButton("Start Review", BUTTON_COLOR);

        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(startReviewButton);

        add(buttonPanel, BorderLayout.SOUTH);

        newButton.addActionListener(e -> addNewFlashcardPair());
        saveButton.addActionListener(e -> saveFlashcard());
        deleteButton.addActionListener(e -> deleteFlashcards());
        startReviewButton.addActionListener(e -> startReview());

        this.setPreferredSize(new Dimension(1000, 560));
    }

    /**
     * Creates a styled JButton for consistent UI appearance.
     *
     * @param text the button label
     * @param bgColor the background color
     * @return a styled JButton
     */
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

    /**
     * Creates a styled text field used for questions and answers.
     *
     * @return a styled JTextField
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(25);
        field.setBackground(INPUT_FIELD_COLOR);
        field.setFont(field.getFont().deriveFont(14f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setPreferredSize(new Dimension(300, 35));
        return field;
    }

    /**
     * Creates a styled label used in the flashcard form.
     *
     * @param text the label text
     * @return a styled JLabel
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        label.setForeground(LABEL_COLOR);
        return label;
    }

    /**
     * Builds the initial form layout for editing a flashcard set.
     * Resets fields and loads the Set Name input.
     */
    private void buildInitialForm() {
        formPanel.removeAll();
        questionFields.clear();
        answerFields.clear();
        questionCounter = 1;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createStyledLabel("Set Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        styleSetNameField();
        formPanel.add(setNameField, gbc);

        addNewFlashcardPair();
        formPanel.revalidate();
        formPanel.repaint();
    }

    /**
     * Applies consistent styling to the Set Name text field.
     */
    private void styleSetNameField() {
        setNameField.setBackground(INPUT_FIELD_COLOR);
        setNameField.setFont(setNameField.getFont().deriveFont(14f));
        setNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        setNameField.setPreferredSize(new Dimension(300, 35));
    }

    /**
     * Adds a new question/answer input row to the form.
     * Automatically adjusts layout and increments the counter.
     */
    private void addNewFlashcardPair() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int baseRow = 1 + (questionFields.size() * 2);

        JTextField questionField = createStyledTextField();
        JTextField answerField = createStyledTextField();

        questionFields.add(questionField);
        answerFields.add(answerField);

        gbc.gridx = 0;
        gbc.gridy = baseRow;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(createStyledLabel("Question " + questionCounter + ":"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(questionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = baseRow + 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(createStyledLabel("Answer:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(answerField, gbc);

        questionCounter++;

        formPanel.revalidate();
        formPanel.repaint();
    }

    /**
     * Saves the current flashcard set using the controller.
     */
    private void saveFlashcard() {
        String setName = setNameField.getText().trim();
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < questionFields.size(); i++) {
            questions.add(questionFields.get(i).getText());
            answers.add(answerFields.get(i).getText());
        }
        controller.saveFlashcards(setName, questions, answers);
    }

    /**
     * Deletes the current flashcard set.
     */
    private void deleteFlashcards() {
        controller.deleteSet();
        buildInitialForm();
    }

    /**
     * Starts reviewing the current flashcard set.
     */
    private void startReview() {
        saveFlashcard();
        viewManagerModel.setState("review flashcards");
        viewManagerModel.firePropertyChange();
    }

    /**
     * Handles state updates from the ViewModel or ViewManager.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == viewManagerModel) {
            controller.ensureCorrectSet();
            return;
        }
        if (!CreateFlashcardViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) {
            return;
        }
        CreateFlashcardState state = (CreateFlashcardState) evt.getNewValue();
        updateForm(state);
    }

    /**
     * Updates all UI fields (set name, questions, answers) to match the latest
     */
    private void updateForm(CreateFlashcardState state) {
        messageLabel.setText(state.getMessage());
        setNameField.setText(state.getSetName());

        formPanel.removeAll();
        questionFields.clear();
        answerFields.clear();
        questionCounter = 1;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createStyledLabel("Set Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        styleSetNameField();
        formPanel.add(setNameField, gbc);

        List<String> questions = state.getQuestions();
        List<String> answers = state.getAnswers();

        if (questions.isEmpty()) {
            addNewFlashcardPair();
        } else {
            for (int i = 0; i < questions.size(); i++) {
                addNewFlashcardPair();
                questionFields.get(i).setText(questions.get(i));
                answerFields.get(i).setText(answers.get(i));
            }
        }

        formPanel.revalidate();
        formPanel.repaint();
    }

    /**
     * Returns the view name used by the ViewManager.
     */
    public String getViewName() {
        return "create flashcard";
    }
}