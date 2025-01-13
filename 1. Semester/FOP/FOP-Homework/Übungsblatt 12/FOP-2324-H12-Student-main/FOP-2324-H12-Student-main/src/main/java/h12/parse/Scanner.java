package h12.parse;

import java.io.IOException;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Class used to read char-wise and provides a token-wise stream
 */
public class Scanner {

    private final CommentFreeReader reader;

    /**
     * Create a new {@link Scanner}
     * @param reader The {@link CommentFreeReader} which has to be used
     * @throws IOException Can be thrown in case of File Problem
     */
    public Scanner(final CommentFreeReader reader) throws IOException {
        this.reader = reader;
        // TODO
    }

    /**
     *
     * @param c Input Char
     * @return true, iff there if the input char is a whitespace
     */
    private static boolean isWhitespace(final char c){
        return  c == '\r' || c == ' ' || c == '\t' || c == '\n';
    }


    /**
     * Read next token
     * @return Return current Token and reads the next one
     * @throws IOException Can be thrown in case of File Problem
     */
    public Token scan() throws IOException {
        // TODO
        return crash();
    }

    /**
     *
     * @return true, iff there is a next Token
     */
    public boolean hasNext(){
        // TODO
        return crash();
    }
}
