package interface_adapter.generate_flashcard;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import data_access.JsonDataAccessObject;
import use_case.ApiKeyDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneratorApiCaller{

    StringBuilder sb = new StringBuilder();

    private String key = "";

    private final JsonDataAccessObject jsonDataAccessObject;

    public GeneratorApiCaller(JsonDataAccessObject jsonDataAccessObject){
        this.jsonDataAccessObject = jsonDataAccessObject;
    }


    public String generateFromSubject(String subject){

        key = jsonDataAccessObject.getApiKey();
        Client client = Client.builder().apiKey(key).build();

        StringBuilder sb = new StringBuilder();

        sb.append("{{\n");
        sb.append("    \"instruction\": \"You are an AI model tasked with generating a structured "
                + "flashcard set based on a user-specified topic. The output must be a single JSON "
                + "object strictly following the defined schema, with no additional commentary, "
                + "explanation, or formatting outside the JSON object.\",\n");

        sb.append("    \"requirements\": {{\n");

        sb.append("        \"flashcards\": 25,\n");

        sb.append("        \"contentStyle\": \"Each flashcard must contain factual, concise, and accurate "
                + "information. Avoid ambiguity, assumptions, or unverifiable claims.\",\n");

        sb.append("        \"questionAnswerBalance\": \"Questions should test understanding or recall, "
                + "and answers must be brief, clear, and directly address the question.\",\n");
        sb.append("        \"format\": {{\n");

        sb.append("            \"jsonSchema\": {{\n");
        sb.append("                \"setName\": \"string (the name of the topic provided by the user)\",\n");
        sb.append("                \"questions\": [\n");
        sb.append("                    {{\n");
        sb.append("                        \"question\": \"string (the flashcard question)\",\n");
        sb.append("                        \"answer\": \"string (the corresponding answer)\"\n");
        sb.append("                    }}\n");
        sb.append("                ]\n");
        sb.append("            }},\n");

        sb.append("            \"keyOrdering\": \"Maintain the exact key order as shown in the schema "
                + "above for every flashcard and the root object.\"\n");

        sb.append("        }},\n");

        sb.append("        \"constraints\": {{\n");

        sb.append("            \"count\": \"Exactly 25 flashcards must be generated — no more, no fewer.\",\n");

        sb.append("            \"accuracy\": \"Do not hallucinate or invent information; use only "
                + "well-established facts related to the topic.\",\n");

        sb.append("            \"clarity\": \"Avoid vague phrasing or overly long answers. Each answer "
                + "should fit naturally on a flashcard.\",\n");

        sb.append("            \"outputOnlyJson\": \"Output must be valid JSON only — no markdown, "
                + "no text before or after.\",\n");

        sb.append("            \"formatIntegrity\": \"Ensure the JSON structure is syntactically correct "
                + "and parsable.\"\n");

        sb.append("        }}\n");
        sb.append("    }},\n");

        sb.append("    \"userPrompt\": \"%s\"\n");
        sb.append("}}\n");

        String promptText = sb.toString();

        String prompt = String.format(promptText, subject);
        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null);

        return response.text();
    }
}
