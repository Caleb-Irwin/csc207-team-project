package interface_adapter.generate_flashcard;

import use_case.generate_flashcard.GeneratorStringParserInterface;


/**
 * The string parser for the Generator use case.
 */

public class GeneratorStringParser implements GeneratorStringParserInterface {

    @Override
    public String parse(String data){
        return data.substring(7, data.length() - 3).trim();
    }
}
