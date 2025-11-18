package view;

import javax.swing.*;

import entity.FlashCard;
import entity.FlashCardSet;
import interface_adapter.login.LoginState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReviewFlashcards {
    private FlashCardSet flashCardSet;
    private int currentCardIndex = 0;
    private boolean showingQuestion = true;

    public void reviewFlashcardsMock() {
        FlashCard flashCard1 = new FlashCard("What is the capital of France?", "Paris");
        FlashCard flashCard2 = new FlashCard("What is 2 + 2?", "4");
        FlashCard flashCard3 = new FlashCard("What is the largest planet in our solar system?", "Jupiter");

        ArrayList<FlashCard> cards = new ArrayList<>();
        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);
        flashCardSet = new FlashCardSet("Test0", cards);

        SwingUtilities.invokeLater(() -> {
            JPanel main = new JPanel();

            // JPanel rightPanel = new JPanel();
            // rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            // JLabel question = new JLabel("What do you want to learn today?");
            // JTextField prompt = new JTextField(10);
            // JButton generate = new JButton("Generate");
            // rightPanel.add(question);
            // rightPanel.add(prompt);
            // rightPanel.add(generate); // make action listener

            // JPanel flashAI = new JPanel();
            // flashAI.setLayout(new BoxLayout(flashAI, BoxLayout.Y_AXIS));
            // JLabel flashAILabel = new JLabel("Flash AI");
            // JButton newset = new JButton("+ New Set"); // make action listener
            // JButton settings = new JButton("Settings"); // make action listener
            // flashAI.add(flashAILabel);
            // flashAI.add(newset);
            // flashAI.add(settings);

            // main.add(flashAI);
            // main.add(rightPanel);

            JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

            // Large text area for displaying questions/answers
            JTextArea contentArea = new JTextArea(10, 40);
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false);
            contentArea.setText("Question or Answer will appear here");
            contentArea.setCursor(null);
            JScrollPane scrollPane = new JScrollPane(contentArea);

            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
            JButton flipCard = new JButton("See Answer");
            JButton nextCard = new JButton("Next >");
            JButton prevCard = new JButton("< Previous");
            controlPanel.add(prevCard);
            controlPanel.add(flipCard);
            controlPanel.add(nextCard);

            prevCard.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (currentCardIndex > 0) {
                                currentCardIndex--;
                            } else {
                                currentCardIndex = flashCardSet.getFlashcards().size() - 1;
                            }

                            showingQuestion = true;
                            flipCard.setText("See Question");
                            contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());
                        }
                    });
            nextCard.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            if (currentCardIndex < flashCardSet.getFlashcards().size() - 1) {
                                currentCardIndex++;
                            } else {
                                currentCardIndex = 0;
                            }

                            showingQuestion = true;
                            flipCard.setName("See Question");
                            contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());
                        }
                    });
            flipCard.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            showingQuestion = !showingQuestion;
                            if (showingQuestion) {
                                contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());
                                flipCard.setText("See Answer");
                            } else {
                                contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getAnswer());
                                flipCard.setText("See Question");
                            }
                        }
                    });

            contentArea.setText(flashCardSet.getFlashcards().get(currentCardIndex).getQuestion());

            reviewPanel.add(scrollPane);
            reviewPanel.add(controlPanel);

            main.add(reviewPanel);

            JFrame frame = new JFrame("HomePage");
            frame.setSize(1000, 700);
            frame.setLocation(650, 500);
            frame.setContentPane(main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
}
