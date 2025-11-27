package view;

import javax.swing.*;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.review_flashcards.ReviewFlashCardsState;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReviewFlashCardsView extends JPanel implements ActionListener, PropertyChangeListener {
    private ReviewFlashCardsViewModel viewModel;
    private final String viewName = "review flashcards";

    private JLabel titleLabel;
    private JTextArea contentArea;
    private JButton flipButton;
    private JButton nextButton;
    private JButton prevButton;

    public ReviewFlashCardsView(ReviewFlashCardsViewModel reviewFlashCardsViewModel) {
        this.viewModel = reviewFlashCardsViewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Large text area for displaying questions/answers
        contentArea = new JTextArea(10, 40);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setText("");
        contentArea.setCursor(null);
        JScrollPane scrollPane = new JScrollPane(contentArea);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        flipButton = new JButton();
        nextButton = new JButton();
        prevButton = new JButton();
        controlPanel.add(prevButton);
        controlPanel.add(flipButton);
        controlPanel.add(nextButton);

        prevButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // if (currentCardIndex > 0) {
                        // currentCardIndex--;
                        // } else {
                        // currentCardIndex = flashCardSet.getFlashcards().size() - 1;
                        // }

                        // showingQuestion = true;
                        // flipCard.setText("See Question");
                        // contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());
                    }
                });
        nextButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // if (currentCardIndex < flashCardSet.getFlashcards().size() - 1) {
                        // currentCardIndex++;
                        // } else {
                        // currentCardIndex = 0;
                        // }

                        // showingQuestion = true;
                        // flipCard.setName("See Question");
                        // contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());
                    }
                });
        flipButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // showingQuestion = !showingQuestion;
                        // if (showingQuestion) {
                        // contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());
                        // flipCard.setText("See Answer");
                        // } else {
                        // contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getAnswer());
                        // flipCard.setText("See Question");
                        // }
                    }
                });

        titleLabel = new JLabel();
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        this.add(titlePanel);
        this.add(scrollPane);
        this.add(controlPanel);

        update(this.viewModel.getState());
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
        final ReviewFlashCardsState state = (ReviewFlashCardsState) evt.getNewValue();
        // setFields(state);
        // usernameErrorField.setText(state.getLoginError());
    }

    private void update(ReviewFlashCardsState state) {
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
            contentArea.setText(currentCard.getQuestion());
        } else {
            contentArea.setText(currentCard.getAnswer());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public static FlashCardSet generateMockFlashCardSet() {
        FlashCard flashCard1 = new FlashCard("What is the capital of France?", "Paris");
        FlashCard flashCard2 = new FlashCard("What is 2 + 2?", "4");
        FlashCard flashCard3 = new FlashCard("What is the largest planet in our solar system?", "Jupiter");

        java.util.ArrayList<FlashCard> cards = new java.util.ArrayList<>();
        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);
        return new FlashCardSet("Test0", cards);
    }
}
