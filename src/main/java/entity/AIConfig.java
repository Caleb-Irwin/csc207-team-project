package entity;

/**
 * Stores the API key for AI-based flashcard generation.
 * Used for connecting to AI services (e.g., OpenRouter) to generate flashcards.
 */

public class AIConfig {
    private String apikey;

    public AIConfig(String apikey) {
        this.apikey = apikey;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
