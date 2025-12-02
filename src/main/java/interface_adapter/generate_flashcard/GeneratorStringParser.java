package interface_adapter.generate_flashcard;

import use_case.generate_flashcard.GeneratorStringParserInterface;

public class GeneratorStringParser implements GeneratorStringParserInterface {

    @Override
    public String parse(String data){
        return data.substring(7, data.length() - 3).trim();
    }
}
