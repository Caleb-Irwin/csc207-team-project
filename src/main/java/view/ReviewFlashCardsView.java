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
import use_case.FlashCardSetsDataAccessInterface;
import use_case.review_flashcards.ReviewFlashCardsActionName;
import use_case.review_flashcards.ReviewFlashCardsInputData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReviewFlashCardsView extends JPanel implements ActionListener, PropertyChangeListener {
    private ReviewFlashCardsViewModel viewModel;
    private FlashCardSetsDataAccessInterface flashCardSetsDAO;
    private ViewManagerModel viewManagerModel;
    private final String viewName = "review flashcards";

    private JLabel titleLabel;
    private JTextPane contentArea;
    private JButton flipButton;
    private JButton nextButton;
    private JButton prevButton;

    private static final Color BACKGROUND_COLOR = new Color(217, 210, 230);
    private static final Color CONTENT_FRONT_COLOR = Color.WHITE;
    private static final Color CONTENT_BACK_COLOR = new Color(180, 180, 180);
    private static final Color BUTTON_COLOR = new Color(229, 115, 180);
    private static final Color NAV_BUTTON_COLOR = new Color(150, 130, 170);

    public ReviewFlashCardsView(ReviewFlashCardsViewModel reviewFlashCardsViewModel,
            ReviewFlashCardsController reviewFlashCardsController,
            FlashCardSetsDataAccessInterface flashCardSetsDAO,
            ViewManagerModel viewManagerModel) {
        this.viewModel = reviewFlashCardsViewModel;
        this.flashCardSetsDAO = flashCardSetsDAO;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

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

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.add(titleLabel);

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
                        reviewFlashCardsController
                                .execute(new ReviewFlashCardsInputData(ReviewFlashCardsActionName.PREVIOUS_CARD,
                                        viewModel.getState().getCurrentCardIndex(),
                                        viewModel.getState().isShowingQuestion()));
                    }
                });
        nextButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        reviewFlashCardsController
                                .execute(new ReviewFlashCardsInputData(ReviewFlashCardsActionName.NEXT_CARD,
                                        viewModel.getState().getCurrentCardIndex(),
                                        viewModel.getState().isShowingQuestion()));
                    }
                });
        flipButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        reviewFlashCardsController
                                .execute(new ReviewFlashCardsInputData(ReviewFlashCardsActionName.FLIP_CARD,
                                        viewModel.getState().getCurrentCardIndex(),
                                        viewModel.getState().isShowingQuestion()));
                    }
                });

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        this.add(controlPanel, gbc);

        this.setPreferredSize(new Dimension(1000, 560));

        update(this.viewModel.getState());
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(130, 35));
        return button;
    }

    /**
     * React to a button click that results in evt.
     * 
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ReviewFlashCardsState state = (ReviewFlashCardsState) evt.getNewValue();
        update(state);
    }

    private void update(ReviewFlashCardsState state) {
        int currentFlashCardSetId = viewManagerModel.getCurrentFlashCardSetId();
        FlashCardSet flashCardSet = flashCardSetsDAO.getFlashCardSetById(currentFlashCardSetId);
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

    private void setContentText(String text) {
        contentArea.setText(text);
        // Re-apply center alignment after setting text
        StyledDocument doc = contentArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private void centerTextVertically() {
        SwingUtilities.invokeLater(() -> {
            int textHeight = contentArea.getPreferredSize().height;
            int viewHeight = contentArea.getParent().getHeight();
            int topPadding = Math.max(15, (viewHeight - textHeight) / 2);
            contentArea.setBorder(BorderFactory.createEmptyBorder(topPadding, 15, 15, 15));
        });
    }

    public String getViewName() {
        return viewName;
    }
}
