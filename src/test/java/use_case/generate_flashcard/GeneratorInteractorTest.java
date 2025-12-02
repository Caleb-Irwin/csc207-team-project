package use_case.generate_flashcard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneratorInteractorTest {

    @Test
    void successTest() {
        GeneratorInputData inputData = new GeneratorInputData("biology");

        GeneratorApiCallerInterface fakeApi = s -> "{ \"setName\": \"biology\", \"questions\": [] }";
        GeneratorStringParserInterface fakeParser = s -> "parsedData";
        GeneratorSetSaverInterface fakeSaver = data -> 123;

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                assertEquals(123, setID);
                assertEquals("biology", subject);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Success test should not fail");
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter, fakeApi, fakeParser, fakeSaver
        );

        interactor.execute(inputData);
    }

    @Test
    void emptySubjectTest() {
        GeneratorInputData inputData = new GeneratorInputData("");

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Please enter subject!", error);
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter,
                s -> "dummy",
                s -> "dummy",
                data -> 1
        );

        interactor.execute(inputData);
    }

    @Test
    void invalidApiTest() {
        GeneratorInputData inputData = new GeneratorInputData("math");

        GeneratorApiCallerInterface fakeApi = s -> "invalidAPI";

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Invalid API key!", error);
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter,
                fakeApi,
                s -> "parsedData",
                data -> 1
        );

        interactor.execute(inputData);
    }

    @Test
    void noApiKeyTest() {
        GeneratorInputData inputData = new GeneratorInputData("history");

        GeneratorApiCallerInterface fakeApi = s -> "noAPI";

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Please enter a proper API key!", error);
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter,
                fakeApi,
                s -> "parsedData",
                data -> 1
        );

        interactor.execute(inputData);
    }

    @Test
    void emptyApiResponseTest() {
        GeneratorInputData inputData = new GeneratorInputData("chemistry");

        GeneratorApiCallerInterface fakeApi = s -> "";

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Something went wrong during generation! Please try again!", error);
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter,
                fakeApi,
                s -> "parsedData",
                data -> 1
        );

        interactor.execute(inputData);
    }

    @Test
    void parserFailureTest() {
        GeneratorInputData inputData = new GeneratorInputData("physics");

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Something went wrong during parsing! Please try again!", error);
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter,
                s -> "{validJson}",
                s -> "",
                data -> 1
        );

        interactor.execute(inputData);
    }

    @Test
    void saverFailureTest() {
        GeneratorInputData inputData = new GeneratorInputData("geography");

        GeneratorOutputBoundary presenter = new GeneratorOutputBoundary() {
            @Override
            public void prepareSuccessView(int setID, String subject) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Something went wrong during saving! Please try again!", error);
            }
        };

        GeneratorInteractor interactor = new GeneratorInteractor(
                presenter,
                s -> "{validJson}",
                s -> "parsedData",
                data -> -1
        );

        interactor.execute(inputData);
    }
}
