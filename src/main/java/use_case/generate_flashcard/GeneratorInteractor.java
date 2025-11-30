package use_case.generate_flashcard;

import interface_adapter.generate_flashcard.GeneratorApiCaller;
import interface_adapter.generate_flashcard.GeneratorJsonParser;
import interface_adapter.generate_flashcard.GeneratorSetSaver;
import org.json.JSONObject;

public class GeneratorInteractor implements GeneratorInputBoundary{

    private final GeneratorOutputBoundary generatorPresenter;
    private final GeneratorApiCaller generatorApiCaller;
    private final GeneratorJsonParser generatorJsonParser;
    private final GeneratorSetSaver generatorSetSaver;



    public GeneratorInteractor(GeneratorOutputBoundary generatorOutputBoundary,
                               GeneratorApiCaller generatorApiCaller,
                               GeneratorJsonParser generatorJsonParser,
                               GeneratorSetSaver generatorSetSaver) {
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
            JSONObject setData = generatorJsonParser.parse(response);
            boolean success = generatorSetSaver.save(setData, subject);

            if (!success){
                generatorPresenter.prepareFailView("Something went wrong! - Please try again!");
            }
            else{
                generatorPresenter.prepareSuccessView();
                // TODO: can return subject name so that it redirects to newly created set?
            }
        }
    }



}
