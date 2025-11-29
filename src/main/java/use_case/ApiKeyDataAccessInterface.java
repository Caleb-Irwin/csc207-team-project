package use_case;

public interface ApiKeyDataAccessInterface {
    String getApiKey();

    void saveApiKey(String apiKey);
}
