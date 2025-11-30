package view;

import javax.swing.*;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.ViewManagerModel;
import interface_adapter.review_flashcards.ReviewFlashCardsController;
import interface_adapter.review_flashcards.ReviewFlashCardsState;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;
import use_case.FlashCardSetsDataAccessInterface;
import use_case.review_flashcards.ReviewFlashCardsActionName;
import use_case.review_flashcards.ReviewFlashCardsInputData;

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
    private JTextArea contentArea;
    private JButton flipButton;
    private JButton nextButton;
    private JButton prevButton;

    public ReviewFlashCardsView(ReviewFlashCardsViewModel reviewFlashCardsViewModel,
            ReviewFlashCardsController reviewFlashCardsController,
            FlashCardSetsDataAccessInterface flashCardSetsDAO,
            ViewManagerModel viewManagerModel) {
        this.viewModel = reviewFlashCardsViewModel;
        this.flashCardSetsDAO = flashCardSetsDAO;
        this.viewManagerModel = viewManagerModel;
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
            contentArea.setText(currentCard.getQuestion());
        } else {
            contentArea.setText(currentCard.getAnswer());
        }
    }

    public String getViewName() {
        return viewName;
    }
}
