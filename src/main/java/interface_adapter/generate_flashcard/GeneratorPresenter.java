package interface_adapter.generate_flashcard;

import interface_adapter.ViewManagerModel;
import use_case.generate_flashcard.GeneratorOutputBoundary;
import interface_adapter.review_flashcards.ReviewFlashCardsViewModel;

public class GeneratorPresenter implements GeneratorOutputBoundary {

    private final GeneratorViewModel generatorViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ReviewFlashCardsViewModel reviewFlashCardsViewModel;


    public GeneratorPresenter(GeneratorViewModel generatorViewModel, ViewManagerModel viewManagerModel, ReviewFlashCardsViewModel reviewFlashCardsViewModel) {

        this.generatorViewModel = generatorViewModel;
        this.viewManagerModel = viewManagerModel;
        this.reviewFlashCardsViewModel = reviewFlashCardsViewModel;
    }

    @Override
    public void prepareSuccessView(int setID, String subject){
        final GeneratorState generatorState = generatorViewModel.getState();
        generatorState.setGeneratorError("");
        viewManagerModel.setCurrentFlashCardSetId(setID);
        this.reviewFlashCardsViewModel.firePropertyChange();

        this.viewManagerModel.setState(reviewFlashCardsViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();


    }

    @Override
    public void prepareFailView(String errorMessage){
        final GeneratorState generatorState = generatorViewModel.getState();
        generatorState.setGeneratorError(errorMessage);
        generatorViewModel.firePropertyChange();

    }
}
