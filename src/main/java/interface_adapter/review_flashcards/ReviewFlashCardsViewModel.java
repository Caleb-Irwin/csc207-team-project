package interface_adapter.review_flashcards;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class ReviewFlashCardsViewModel extends ViewModel<ReviewFlashCardsState> {

    public ReviewFlashCardsViewModel() {
        super("review flashcards");
        setState(new ReviewFlashCardsState());
    }

}
