package use_case.settings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Fake in-memory data access object
class InMemorySettingsDAO implements SettingsDataAccessInterface {
    String apiKey = "";

    @Override
    public void saveSettings(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String loadApiKey() {
        return apiKey;
    }
}

// Fake presenter to capture output
class PresenterSpy implements SettingsOutputBoundary {
    SettingsOutputData receivedData = null;

    @Override
    public void prepareSuccessView(SettingsOutputData outputData) {
        this.receivedData = outputData;
    }
}

public class SettingsInteractorTest {

    @Test
    void testSaveSettingsSavesApiKey() {
        // Arrange
        InMemorySettingsDAO dao = new InMemorySettingsDAO();
        PresenterSpy presenter = new PresenterSpy();
        SettingsInteractor interactor = new SettingsInteractor(dao, presenter);

        SettingsInputData inputData = new SettingsInputData("TEST_KEY");

        // Act
        interactor.saveSettings(inputData);

        // Assert
        assertEquals("TEST_KEY", dao.loadApiKey(), "DAO should store API key");
    }

    @Test
    void testInteractorCallsPresenter() {
        InMemorySettingsDAO dao = new InMemorySettingsDAO();
        PresenterSpy presenter = new PresenterSpy();
        SettingsInteractor interactor = new SettingsInteractor(dao, presenter);

        SettingsInputData inputData = new SettingsInputData("ABC123");
        interactor.saveSettings(inputData);

        assertNotNull(presenter.receivedData, "Presenter should receive output");
        assertEquals("ABC123", presenter.receivedData.getApiKey());
    }

    @Test
    void testSavingEmptyApiKeyAllowed() {
        InMemorySettingsDAO dao = new InMemorySettingsDAO();
        PresenterSpy presenter = new PresenterSpy();
        SettingsInteractor interactor = new SettingsInteractor(dao, presenter);

        SettingsInputData inputData = new SettingsInputData("");

        interactor.saveSettings(inputData);

        assertEquals("", dao.loadApiKey());
        assertEquals("", presenter.receivedData.getApiKey());
    }
}
