package use_case.generate_flashcard;



/**
 * The interactor for the Generator use case.
 */

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
                    generatorPresenter.prepareFailView(
                            "Something went wrong during generation!");
                    return;
                }
                case "noAPI" -> {
                    generatorPresenter.prepareFailView("Please enter a proper API key!");
                    return;
                }
                default -> {
//                    for checkstyle pass
                }
            }
            String setData = generatorJsonParser.parse(response);
            if (setData.isEmpty()) {
                generatorPresenter.prepareFailView("Something went wrong during parsing! " +
                        "Please try again!");
                return;
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
