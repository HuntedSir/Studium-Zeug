package h12.export;


import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.State;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Implementation of {@link FsmExporter}, which can be used to export {@link Fsm} as kiss2-File
 */
public class KissExporter implements FsmExporter {
    private final BufferedWriter writer;

    /**
     * Creates a new instance of q {@link KissExporter}
     * @param writer Writer which is used as output
     */
    public KissExporter(final BufferedWriter writer){
        this.writer = writer;
    }

    @Override
    public void export(final Fsm fsm) throws IOException {
       // TODO
    }
}
