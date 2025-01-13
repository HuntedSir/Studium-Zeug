package h12.parse;

import h12.template.errors.BadIdentifierException;
import h12.template.errors.BadNumberException;
import h12.template.errors.BadTokenException;
import h12.template.errors.KissParserException;
import h12.template.fsm.BitField;

import java.io.IOException;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Class implementing a parser for the kiss2 file
 */
public class FsmParser {

    private final Scanner scanner;
    private final FsmBuilder builder;

    private Token currentToken;

    /**
     * Constructs a new {@link FsmParser}
     * @param scanner A scanner used as {@link Token} stream
     * @param builder The {@link FsmBuilder} used to build the {@link h12.template.fsm.Fsm}
     * @throws IOException Can be thrown in case of File Problem
     */
    public FsmParser(final Scanner scanner, final FsmBuilder builder) throws IOException {
        this.scanner = scanner;
        this.builder = builder;

        currentToken = scanner.scan();
    }

    /**
     * Consumes the current Token
     * @return the current Token
     * @throws IOException Can be thrown in case of File Problem
     */
    private Token consumeToken() throws IOException {
        final Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    /**
     * Consumes and checks the current Token
     * @param type the expected Type of the current Token
     * @return the current Token
     * @throws IOException Can be thrown in case of File Problem
     * @throws KissParserException If there is a Token missmatch
     */
    private Token consumeAndCheckToken(final Token.Type type) throws IOException, KissParserException {
        if(!currentToken.is(type)){
            throw new BadTokenException(currentToken, type);
        }

        final Token oldToken = currentToken;
        currentToken = scanner.scan();
        return oldToken;
    }

    /**
     * Parse the Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    public void parse() throws KissParserException, IOException {
        parseFSM();
    }

    /**
     * Parse the Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */

    private void parseFSM() throws IOException, KissParserException {
        // TODO
        crash();
    }

    /**
     * Parse the Header
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseHeader() throws IOException, KissParserException {
        while (true){
            if(currentToken.is(Token.Type.KEYWORD_INPUT_WIDTH)){
                parseInputWidth();
            }else{ // TODO
                // TODO
                crash();
                return;
            }
        }

    }

    /**
     * Parse the Input Width
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseInputWidth() throws IOException, KissParserException {
        consumeAndCheckToken(Token.Type.KEYWORD_INPUT_WIDTH);

        if(currentToken.is(Token.Type.NUMBER)){
            builder.setInputSize(Integer.parseInt(currentToken.getValue()));
            consumeToken();
        }else{
            throw new BadNumberException(currentToken);
        }
    }


    /**
     * Parse the Output Width
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseOutputWidth() throws IOException, KissParserException {
        // TODO
        crash();
    }

    /**
     * Parse the Number of Terms
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseNumberOfTerms() throws IOException, KissParserException {
        // TODO
        crash();
    }

    /**
     * Parse the Number of States
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseNumberOfStates() throws IOException, KissParserException {
        // TODO
        crash();
    }

    /**
     * Parse the Initial State of Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseInitialState() throws IOException, KissParserException {
        // TODO
        crash();
    }

    /**
     * Parse all Terms
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseTerms() throws KissParserException, IOException {
        // TODO
        crash();
    }

    /**
     * Parse a single Term of Automata
     * @throws KissParserException Thrown when there is a Parser Error
     * @throws IOException Can be thrown in case of File Problem
     */
    private void parseTerm() throws KissParserException, IOException {
        // TODO
        crash();
    }

}
