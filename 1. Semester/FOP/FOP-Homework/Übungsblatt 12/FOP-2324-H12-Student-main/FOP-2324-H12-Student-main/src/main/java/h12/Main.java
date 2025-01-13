package h12;

import h12.export.DotExporter;
import h12.ioFactory.GraphvizOnlineIOFactory;
import h12.ioFactory.ResourceIOFactory;
import h12.template.errors.KissParserException;
import h12.template.fsm.Fsm;
import h12.ioFactory.IOFactory;
import h12.parse.CommentFreeReader;
import h12.parse.FsmBuilderImpl;
import h12.parse.FsmParser;
import h12.parse.Scanner;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(final String[] args) throws IOException, KissParserException {

        final IOFactory ioFactory = new ResourceIOFactory();
        final BufferedReader reader = ioFactory.createReader("h12/rs_latch.kiss2");
        final CommentFreeReader commentFreeReader = new CommentFreeReader(reader);
        final Scanner scanner = new Scanner(commentFreeReader);

        final FsmBuilderImpl fsmBuilder = new FsmBuilderImpl();
        final FsmParser fsmParser = new FsmParser(scanner, fsmBuilder);

        fsmParser.parse();

        final Fsm fsm = fsmBuilder.getFsm();

        final GraphvizOnlineIOFactory.GraphvizOnlineURLWriter graphvizOnlineURLWriter = (new GraphvizOnlineIOFactory()).createWriter(null);
        final DotExporter exporter = new DotExporter(graphvizOnlineURLWriter);
        exporter.export(fsm);
        System.out.println("URL:\t" + graphvizOnlineURLWriter.getURL());
    }
}
