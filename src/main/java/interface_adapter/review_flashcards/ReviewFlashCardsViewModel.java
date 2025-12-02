package interface_adapter.review_flashcards;

import interface_adapter.ViewModel;

/**
 * The View Model for the Review Flashcards View.
 * Manages the state and property change notifications for the flashcard review
 * feature.
 */
public class ReviewFlashCardsViewModel extends ViewModel<ReviewFlashCardsState> {

    /**
     * Constructs a ReviewFlashCardsViewModel with a default state.
     * Initializes the view name to "review flashcards".
     */
    public ReviewFlashCardsViewModel() {
        super("review flashcards");
        setState(new ReviewFlashCardsState());
    }

}
