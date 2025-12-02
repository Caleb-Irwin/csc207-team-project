package interface_adapter.generate_flashcard;

import data_access.JsonDataAccessObject;
import entity.FlashCard;
import entity.FlashCardSet;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.generate_flashcard.GeneratorSetSaverInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The set saver for generator use case.
 */


public class GeneratorSetSaver implements GeneratorSetSaverInterface {

    private final JsonDataAccessObject jsonDataAccessObject;


    public GeneratorSetSaver(JsonDataAccessObject jsonDataAccessObject){
        this.jsonDataAccessObject = jsonDataAccessObject;
    }

    @Override
    public int save(String jsonString) {
        int id;


        JSONObject obj = new JSONObject(jsonString);


        String setName = obj.getString("setName");


        JSONArray arr = obj.getJSONArray("questions");

        List<FlashCard> flashcards = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject qObj = arr.getJSONObject(i);

            String question = qObj.getString("question");
            String answer = qObj.getString("answer");

            flashcards.add(new FlashCard(question, answer));
        }

        id = jsonDataAccessObject.getNextAvailableId();


        FlashCardSet flashCardSet = new FlashCardSet(setName, flashcards, id);
        flashCardSet.setFlashcards(flashcards);
        jsonDataAccessObject.createFlashCardSet(flashCardSet);
        return id;
    }
}
