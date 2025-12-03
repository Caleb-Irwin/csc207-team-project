package view;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsState;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for reviewing flashcards.
 * Displays flashcards one at a time with controls to flip, navigate, and edit.
 */
public class ReviewFlashCardsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final ReviewFlashCardsViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final ReviewFlashCardsController controller;
    private final String viewName = "review flashcards";

    private JLabel titleLabel;
    private JTextPane contentArea;
    private JButton flipButton;
    private JButton nextButton;
    private JButton prevButton;
    private JButton editButton;

    private static final Color BACKGROUND_COLOR = new Color(217, 210, 230);
    private static final Color CONTENT_FRONT_COLOR = Color.WHITE;
    private static final Color CONTENT_BACK_COLOR = new Color(180, 180, 180);
    private static final Color BUTTON_COLOR = new Color(229, 115, 180);
    private static final Color NAV_BUTTON_COLOR = new Color(150, 130, 170);

    /**
     * Constructs a ReviewFlashCardsView with the specified view model, controller,
     * and view manager.
     *
     * @param reviewFlashCardsViewModel  the view model for this view
     * @param reviewFlashCardsController the controller handling user actions
     * @param viewManagerModel           the view manager model for navigation
     */
    public ReviewFlashCardsView(ReviewFlashCardsViewModel reviewFlashCardsViewModel,
            ReviewFlashCardsController reviewFlashCardsController,
            ViewManagerModel viewManagerModel) {
        this.viewModel = reviewFlashCardsViewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
        this.controller = reviewFlashCardsController;

        this.setLayout(new GridBagLayout());
        this.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        titleLabel = new JLabel();
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        editButton = createStyledButton("Edit Set", BUTTON_COLOR);
        editButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        reviewFlashCardsController.editSet();
                    }
                });

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.add(editButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        this.add(titlePanel, gbc);

        // Large text area for displaying questions/answers
        contentArea = new JTextPane();
        contentArea.setEditable(false);
        contentArea.setText("");
        contentArea.setCursor(null);
        contentArea.setBackground(CONTENT_FRONT_COLOR);
        contentArea.setFont(contentArea.getFont().deriveFont(32f));
        contentArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Center text horizontally
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(500, 200));

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(scrollPane, gbc);

        // Control panel with buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(BACKGROUND_COLOR);

        prevButton = createStyledButton("< Previous", NAV_BUTTON_COLOR);
        flipButton = createStyledButton("Flip", BUTTON_COLOR);
        nextButton = createStyledButton("Next >", NAV_BUTTON_COLOR);

        controlPanel.add(prevButton);
        controlPanel.add(flipButton);
        controlPanel.add(nextButton);

        prevButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        reviewFlashCardsController.previousQuestion();
                    }
                });
        nextButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        reviewFlashCardsController.nextQuestion();
                    }
                });
        flipButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        reviewFlashCardsController.flipCard();
                    }
                });

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        this.add(controlPanel, gbc);

        this.setPreferredSize(new Dimension(1000, 560));
        update(this.viewModel.getState());
    }

    /**
     * Creates a styled button with the specified text and background color.
     *
     * @param text    the button text
     * @param bgColor the background color of the button
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
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }

    /**
     * Reacts to a button click event.
     *
     * @param evt the ActionEvent to react to
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    /**
     * Responds to property change events from the view model or view manager.
     * Updates the view when the state changes.
     *
     * @param evt the PropertyChangeEvent containing the state change information
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == viewManagerModel) {
            update(viewModel.getState());
        } else {
            ReviewFlashCardsState state = (ReviewFlashCardsState) evt.getNewValue();
            update(state);
        }
    }

    /**
     * Updates the view based on the current state.
     * Refreshes the displayed flashcard, buttons, and title based on state data.
     *
     * @param state the current ReviewFlashCardsState
     */
    private void update(ReviewFlashCardsState state) {
        int flashCardSetId = state.getFlashCardSetId();
        controller.ensureCorrectSet(flashCardSetId);
        FlashCardSet flashCardSet = state.getFlashCardSet();

        int currentCardIndex = state.getCurrentCardIndex();
        boolean showingQuestion = state.isShowingQuestion();

        if (showingQuestion) {
            flipButton.setText("See Answer");
        } else {
            flipButton.setText("See Question");
        }
        nextButton.setText("Next >");
        prevButton.setText("< Previous");

        // Update Text Area
        if (flashCardSet == null || flashCardSet.getFlashcards().isEmpty()) {
            contentArea.setText("No flashcards available.");
            flipButton.setEnabled(false);
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
            titleLabel.setText("Empty Flashcard Set");
            return;
        }

        flipButton.setEnabled(true);
        nextButton.setEnabled(true);
        prevButton.setEnabled(true);

        titleLabel.setText("Reviewing Set: " + flashCardSet.getSetName() + " ("
                + (currentCardIndex + 1) + "/" + flashCardSet.getFlashcards().size() + ")");

        FlashCard currentCard = flashCardSet.getFlashcards().get(currentCardIndex);
        if (showingQuestion) {
            contentArea.setBackground(CONTENT_FRONT_COLOR);
            setContentText(currentCard.getQuestion());
        } else {
            contentArea.setBackground(CONTENT_BACK_COLOR);
            setContentText(currentCard.getAnswer());
        }
        centerTextVertically();
    }

    /**
     * Sets the text content of the flashcard display area with center alignment.
     *
     * @param text the text to display
     */
    private void setContentText(String text) {
        contentArea.setText(text);
        // Re-apply center alignment after setting text
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    /**
     * Centers the text vertically within the content area.
     * Adjusts padding based on the text and view heights.
     */
    private void centerTextVertically() {
        SwingUtilities.invokeLater(() -> {
            int textHeight = contentArea.getPreferredSize().height;
            int viewHeight = contentArea.getParent().getHeight();
            int topPadding = Math.max(15, (viewHeight - textHeight) / 2);
            contentArea.setBorder(BorderFactory.createEmptyBorder(topPadding, 15, 15, 15));
        });
    }

    /**
     * Returns the name of this view.
     *
     * @return the view name string
     */
    public String getViewName() {
        return viewName;
    }
}