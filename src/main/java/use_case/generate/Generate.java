package use_case.generate;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;

public class Generate {
    public static void main(String[] args) throws IOException {

        Dotenv dotenv = Dotenv.load();
        String googleApiKey = dotenv.get("GOOGLE_API_KEY");

        Client client = Client.builder().apiKey(googleApiKey).build();

        String subject = "Calculus";
        String prompt = String.format("{{\n" +
                "    \"instruction\": \"You are an AI model tasked with generating a structured flashcard set based on a user-specified topic. The output must be a single JSON object strictly following the defined schema, with no additional commentary, explanation, or formatting outside the JSON object.\",\n"
                +
                "\n" +
                "    \"requirements\": {{\n" +
                "        \"flashcards\": 25,\n" +
                "        \"contentStyle\": \"Each flashcard must contain factual, concise, and accurate information. Avoid ambiguity, assumptions, or unverifiable claims.\",\n"
                +
                "        \"questionAnswerBalance\": \"Questions should test understanding or recall, and answers must be brief, clear, and directly address the question.\",\n"
                +
                "        \"reviewField\": \"Every flashcard must include a 'review' field initialized to 0.\",\n" +
                "        \"format\": {{\n" +
                "            \"jsonSchema\": {{\n" +
                "                \"setName\": \"string (the name of the topic provided by the user)\",\n" +
                "                \"questions\": [\n" +
                "                    {{\n" +
                "                        \"question\": \"string (the flashcard question)\",\n" +
                "                        \"answer\": \"string (the corresponding answer)\",\n" +
                "                        \"review\": 0\n" +
                "                    }}\n" +
                "                ]\n" +
                "            }},\n" +
                "            \"keyOrdering\": \"Maintain the exact key order as shown in the schema above for every flashcard and the root object.\"\n"
                +
                "        }},\n" +
                "        \"constraints\": {{\n" +
                "            \"count\": \"Exactly 25 flashcards must be generated — no more, no fewer.\",\n" +
                "            \"accuracy\": \"Do not hallucinate or invent information; use only well-established facts related to the topic.\",\n"
                +
                "            \"clarity\": \"Avoid vague phrasing or overly long answers. Each answer should fit naturally on a flashcard.\",\n"
                +
                "            \"outputOnlyJson\": \"Output must be valid JSON only — no markdown, no text before or after.\",\n"
                +
                "            \"formatIntegrity\": \"Ensure the JSON structure is syntactically correct and parsable.\"\n"
                +
                "        }}\n" +
                "    }},\n" +
                "    \n" +
                "    \"userPrompt\": \"%s\"\n" +
                "}}\n", subject);

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null);

        String setString = response.text();

        String trimString = setString.substring(7, setString.length() - 3).trim();
        JSONObject json = new JSONObject(trimString);

        String savePath = String.format("./data/%s.json", subject);
        Files.writeString(
                Path.of(savePath),
                json.toString(4) // pretty format
        );

        System.out.println("Saved JSON: " + savePath);

    }
}
