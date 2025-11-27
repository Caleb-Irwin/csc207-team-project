package interface_adapter.generate_flashcard;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneratorApiCaller {

    public String generateFromSubject(String subject){
        Client client = Client.builder().apiKey("AIzaSyAAntyeGewVrpiyKjqXMFr084wVUtYDB90").build();

        String promptText;
        try {
            promptText = new String(Files.readAllBytes(Paths.get("prompt.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String prompt = String.format(promptText, subject);
        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null);

        return response.text();
    }
}
