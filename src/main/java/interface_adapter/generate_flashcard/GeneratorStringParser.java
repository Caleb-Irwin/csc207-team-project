package interface_adapter.generate_flashcard;


public class GeneratorStringParser {

    public String parse(String data){
        return data.substring(7, data.length() - 3).trim();
    }
}
