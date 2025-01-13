package h12.ioFactory;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * An Implementation of {@link IOFactory} for the Graphviz Online URL
 */
public class GraphvizOnlineIOFactory implements IOFactory {

    /**
     * Subtype of Buffered Reader used to capture content of File
     */

    public static class GraphvizOnlineURLWriter extends BufferedWriter {
        private static final String BASE_URL = "https://dreampuf.github.io/GraphvizOnline/#";

        private final StringWriter stringWriter;

        private GraphvizOnlineURLWriter(final StringWriter stringWriter) {
            super(stringWriter);
            this.stringWriter = stringWriter;
        }

        /**
         * Create a new empty {@link GraphvizOnlineURLWriter}
         *
         * @return the created {@link GraphvizOnlineURLWriter}
         */
        public static GraphvizOnlineURLWriter create() {
            return new GraphvizOnlineURLWriter(new StringWriter());
        }

        /**
         * @return corresponding URL
         * @throws IOException /
         */
        public String getURL() throws IOException {
            flush();
            return BASE_URL + URLEncoder.encode(stringWriter.toString(), StandardCharsets.UTF_8).replace("+", "%20");
        }
    }


    @Override
    public boolean supportsReader() {
        return false;
    }

    @Override
    public BufferedReader createReader(final String ioName) throws IOException {
        throw new UnsupportedOperationException("GraphvizOnlineIOFactory does not support writing!");
    }

    @Override
    public boolean supportsWriter() {
        return true;
    }

    @Override
    public GraphvizOnlineURLWriter createWriter(final String ioName) throws IOException {
        return GraphvizOnlineURLWriter.create();
    }
}
