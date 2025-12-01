package use_case.generate_flashcard;

import interface_adapter.generate_flashcard.GeneratorApiCaller;
import interface_adapter.generate_flashcard.GeneratorStringParser;
import interface_adapter.generate_flashcard.GeneratorSetSaver;

public class GeneratorInteractor implements GeneratorInputBoundary{

    private final GeneratorOutputBoundary generatorPresenter;
    private final GeneratorApiCallerInterface generatorApiCaller;
    private final GeneratorStringParserInterface generatorJsonParser;
    private final GeneratorSetSaverInterface generatorSetSaver;



    public GeneratorInteractor(GeneratorOutputBoundary generatorOutputBoundary,
                               GeneratorApiCallerInterface generatorApiCaller,
                               GeneratorStringParserInterface generatorJsonParser,
                               GeneratorSetSaverInterface generatorSetSaver) {
        this.generatorPresenter = generatorOutputBoundary;
        this.generatorApiCaller = generatorApiCaller;
        this.generatorJsonParser = generatorJsonParser;
        this.generatorSetSaver = generatorSetSaver;
    }


    @Override
    public void execute(GeneratorInputData generatorInputData) {
        final String subject = generatorInputData.getSubject();
        if (subject.isEmpty()) {
            generatorPresenter.prepareFailView("Please enter subject!");
        }
        else{
            String response = generatorApiCaller.generateFromSubject(subject);
            switch (response) {
                case "invalidAPI" -> {
                    generatorPresenter.prepareFailView("Invalid API key!");
                    return;
                }
                case "" -> {
                    generatorPresenter.prepareFailView("Something went wrong during generation! " +
                            "Please try again!");
                    return;
                }
                case "noAPI" -> {
                    generatorPresenter.prepareFailView("Please enter a proper API key!");
                    return;
                }
            }
            String setData = generatorJsonParser.parse(response);
            if (setData.isEmpty()) {
                generatorPresenter.prepareFailView("Something went wrong during parsing! " +
                        "Please try again!");
            }
            int setID = generatorSetSaver.save(setData);

            if (setID<0){
                generatorPresenter.prepareFailView("Something went wrong during saving! " +
                        "Please try again!");
            }
            else{
                generatorPresenter.prepareSuccessView(setID, subject);

            }
        }
    }



}
