package interface_adapter.generate_flashcard;

import org.json.JSONObject;

public class GeneratorJsonParser {

    public JSONObject parse(String data){
        String trimString = data.substring(7, data.length() - 3).trim();
        return new JSONObject(trimString);
    }
}
