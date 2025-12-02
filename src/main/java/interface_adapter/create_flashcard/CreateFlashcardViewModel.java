package interface_adapter.create_flashcard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for Create Flashcard use case.
 * <p>
 * The ViewModel for the Create Flashcard use case manages the state of the view. It holds a reference to
 * the current state of the flashcard set being created (set name, questions, answers, etc.) and notifies
 * listeners when the state changes.
 */
public class CreateFlashcardViewModel {
    public static final String STATE_PROPERTY = "state";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CreateFlashcardState state = new CreateFlashcardState();

    /**
     * Gets the current state of the Create Flashcard use case.
     *
     * @return the current state
     */
    public CreateFlashcardState getState() {
        return state;
    }

    /**
     * Sets the current state of the Create Flashcard use case and fires a property change event to notify
     * listeners of the state change.
     *
     * @param state the new state to set
     */
    public void setState(CreateFlashcardState state) {
        CreateFlashcardState oldState = this.state;
        this.state = state;
        support.firePropertyChange(STATE_PROPERTY, oldState, this.state);
    }

    /**
     * Adds a listener to monitor changes in the state of the Create Flashcard use case.
     *
     * @param listener the listener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}