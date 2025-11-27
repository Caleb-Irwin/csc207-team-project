package interface_adapter.generate_flashcard;

import interface_adapter.ViewModel;

public class GeneratorViewModel extends ViewModel<GeneratorState> {
    public GeneratorViewModel() {
        super("generator");
        setState(new GeneratorState());
    }
}
