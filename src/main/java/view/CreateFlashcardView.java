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

    private final JButton newButton;
    private final JButton saveButton;
    private final JButton deleteButton;
    private final JButton startReviewButton;

    private JPanel formPanel;
    private int questionCounter = 1;

    private final CreateFlashcardController controller;
    private final CreateFlashcardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateFlashcardView(CreateFlashcardViewModel viewModel,
            CreateFlashcardController controller,
            ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Header panel with title and message
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

        // Form panel
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        buildInitialForm();

        // Buttons section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        newButton = createStyledButton("+ New Card", NAV_BUTTON_COLOR);
        saveButton = createStyledButton("Save", BUTTON_COLOR);
        deleteButton = createStyledButton("Delete Set", NAV_BUTTON_COLOR);
        startReviewButton = createStyledButton("Start Review", BUTTON_COLOR);

        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(startReviewButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button listeners
        newButton.addActionListener(e -> addNewFlashcardPair());
        saveButton.addActionListener(e -> saveFlashcard());
        deleteButton.addActionListener(e -> deleteFlashcards());
        startReviewButton.addActionListener(e -> startReview());

        this.setPreferredSize(new Dimension(1000, 560));
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

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        label.setForeground(LABEL_COLOR);
        return label;
    }

    /** ------------------- FORM BUILDING ------------------- **/

    private void buildInitialForm() {
        formPanel.removeAll();
        questionFields.clear();
        answerFields.clear();
        questionCounter = 1;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Set Name
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

    private void styleSetNameField() {
        setNameField.setBackground(INPUT_FIELD_COLOR);
        setNameField.setFont(setNameField.getFont().deriveFont(14f));
        setNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        setNameField.setPreferredSize(new Dimension(300, 35));
    }

    private void addNewFlashcardPair() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int baseRow = 1 + (questionFields.size() * 2);

        JTextField questionField = createStyledTextField();
        JTextField answerField = createStyledTextField();

        questionFields.add(questionField);
        answerFields.add(answerField);

        // Question label
        gbc.gridx = 0;
        gbc.gridy = baseRow;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(createStyledLabel("Question " + questionCounter + ":"), gbc);

        // Question input
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(questionField, gbc);

        // Answer label
        gbc.gridx = 0;
        gbc.gridy = baseRow + 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(createStyledLabel("Answer:"), gbc);

        // Answer input
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(answerField, gbc);

        questionCounter++;

        formPanel.revalidate();
        formPanel.repaint();
    }

    /** ------------------- SAVE LOGIC ------------------- **/

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

    /** ------------------- DELETE LOGIC ------------------- **/

    private void deleteFlashcards() {
        controller.deleteSet();
        buildInitialForm();
    }

    private void startReview() {
        saveFlashcard(); // Ensure latest changes are saved
        // Navigate to ReviewFlashCardsView
        viewManagerModel.setState("review flashcards");
        viewManagerModel.firePropertyChange();
    }

    /** ------------------- VIEWMODEL UPDATE ------------------- **/

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == viewManagerModel) {
            // View navigation changed, ensure we have the correct set loaded
            controller.ensureCorrectSet();
            return;
        }

        if (!CreateFlashcardViewModel.STATE_PROPERTY.equals(evt.getPropertyName())) {
            return;
        }

        CreateFlashcardState state = (CreateFlashcardState) evt.getNewValue();
        updateForm(state);
    }

    private void updateForm(CreateFlashcardState state) {
        messageLabel.setText(state.getMessage());
        setNameField.setText(state.getSetName());

        // Rebuild form with the state's questions/answers
        formPanel.removeAll();
        questionFields.clear();
        answerFields.clear();
        questionCounter = 1;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Set Name
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
            // Add one empty pair for new sets
            addNewFlashcardPair();
        } else {
            // Populate with existing questions/answers
            for (int i = 0; i < questions.size(); i++) {
                addNewFlashcardPair();
                questionFields.get(i).setText(questions.get(i));
                answerFields.get(i).setText(answers.get(i));
            }
        }

        formPanel.revalidate();
        formPanel.repaint();
    }

    public String getViewName() {
        return "create flashcard";
    }
}