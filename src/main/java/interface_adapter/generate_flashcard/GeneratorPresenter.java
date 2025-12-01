package interface_adapter.generate_flashcard;

import interface_adapter.ViewManagerModel;
import use_case.generate_flashcard.GeneratorOutputBoundary;

public class GeneratorPresenter implements GeneratorOutputBoundary {

    private final GeneratorViewModel generatorViewModel;
    private final ViewManagerModel viewManagerModel;


    public GeneratorPresenter(GeneratorViewModel generatorViewModel, ViewManagerModel viewManagerModel) {

        this.generatorViewModel = generatorViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(){
        // I need something like this
//        public void prepareSuccessView(GenerateOutputData outputData) {
//
//            String setName = outputData.getSetName();
//            int setId = outputData.getSetId();
//
//            viewManagerModel.setState("SET_CREATED:" + "setName" + ":" + "setId");
//            viewManagerModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String errorMessage){
        final GeneratorState generatorState = generatorViewModel.getState();
        generatorState.setGeneratorError(errorMessage);
        generatorViewModel.firePropertyChange();

    }
}
