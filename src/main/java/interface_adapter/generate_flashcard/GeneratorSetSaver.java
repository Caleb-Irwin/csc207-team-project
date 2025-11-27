package interface_adapter.generate_flashcard;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneratorSetSaver {

    public boolean save(JSONObject data, String subject) {
        if (data.isEmpty()){
            return false;
        }
        else{
            String savePath = String.format("./data/%s.json", subject);
            try {
                Files.writeString(
                        Path.of(savePath),
                        data.toString(4)
                );
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

}
