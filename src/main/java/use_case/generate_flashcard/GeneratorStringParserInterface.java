package use_case.generate_flashcard;


/**
 * The interface for parsing string (LLM output) in the generator use_case.
 */

public interface GeneratorStringParserInterface {

    String parse(String data);
}
