package interface_adapter.create_flashcard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateFlashcardViewModel {

    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CreateFlashcardState state = new CreateFlashcardState();

    public CreateFlashcardState getState() {
        return state;
    }

    public void setState(CreateFlashcardState state) {
        CreateFlashcardState oldState = this.state;
        this.state = state;
        support.firePropertyChange(STATE_PROPERTY, oldState, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}