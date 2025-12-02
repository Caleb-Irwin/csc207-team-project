package interface_adapter.generate_flashcard;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import data_access.JsonDataAccessObject;
import use_case.generate_flashcard.GeneratorApiCallerInterface;


public class GeneratorApiCaller implements GeneratorApiCallerInterface {


    private final JsonDataAccessObject jsonDataAccessObject;

    public GeneratorApiCaller(JsonDataAccessObject jsonDataAccessObject){
        this.jsonDataAccessObject = jsonDataAccessObject;
    }

    @Override
    public String generateFromSubject(String subject){
        String key = "";

        key = jsonDataAccessObject.getApiKey();
        if("".equals(key)){
            return "noAPI";
        }
        Client client = Client.builder().apiKey(key).build();

        String promptText = """
                {{
                    "instruction": "You are an AI model tasked with generating a structured \
                flashcard set based on a user-specified topic. The output must be a single JSON \
                object strictly following the defined schema, with no additional commentary, \
                explanation, or formatting outside the JSON object.",
                    "requirements": {{
                        "flashcards": 25,
                        "contentStyle": "Each flashcard must contain factual, concise, and accurate \
                information. Avoid ambiguity, assumptions, or unverifiable claims.",
                        "questionAnswerBalance": "Questions should test understanding or recall, \
                and answers must be brief, clear, and directly address the question.",
                        "format": {{
                            "jsonSchema": {{
                                "setName": "string (the name of the topic provided by the user)",
                                "questions": [
                                    {{
                                        "question": "string (the flashcard question)",
                                        "answer": "string (the corresponding answer)"
                                    }}
                                ]
                            }},
                            "keyOrdering": "Maintain the exact key order as shown in the schema \
                above for every flashcard and the root object."
                        }},
                        "constraints": {{
                            "count": "Exactly 25 flashcards must be generated — no more, no fewer.",
                            "accuracy": "Do not hallucinate or invent information; use only \
                well-established facts related to the topic.",
                            "clarity": "Avoid vague phrasing or overly long answers. Each answer \
                should fit naturally on a flashcard.",
                            "outputOnlyJson": "Output must be valid JSON only — no markdown, \
                no text before or after.",
                            "formatIntegrity": "Ensure the JSON structure is syntactically correct \
                and parsable."
                        }}
                    }},
                    "userPrompt": "%s"
                }}
                """;

        String prompt = String.format(promptText, subject);
        try{
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null);
            return response.text();
        }catch (Exception e){
            return "invalidAPI";
        }


    }
}
