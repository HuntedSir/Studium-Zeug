package h12.export;

import h12.template.fsm.BitField;
import h12.template.fsm.Fsm;
import h12.template.fsm.State;
import h12.template.fsm.StateEncoding;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Implementation of {@link FsmExporter}, which can be used to export {@link Fsm} as System Verilog-File
 */
public class SystemVerilogExporter implements FsmExporter {

    private final BufferedWriter writer;
    private final StateEncoding stateEncoding;
    private final String moduleName;

    /**
     * Constructs a instance of q {@link SystemVerilogExporter}
     *
     * @param writer        Writer which is used to write the output
     * @param stateEncoding {@link StateEncoding} which is used to encode the States of exported {@link Fsm}
     * @param moduleName    The name of the Module for System Verilog Header
     */
    public SystemVerilogExporter(final BufferedWriter writer, final StateEncoding stateEncoding, final String moduleName) {
        this.writer = writer;
        this.stateEncoding = stateEncoding;
        this.moduleName = moduleName;
    }

    /**
     * Writes the Module Header
     *
     * @param inputBitWidth The Bit Width of Input
     * @param outputBitWith The Bit Width of Output
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateModuleHeader(final int inputBitWidth, final int outputBitWith) throws IOException {
        writer.write("module ");
        writer.write(moduleName);
        writer.write("(input clk, input rst, input [");
        writer.write(String.valueOf(inputBitWidth - 1));
        writer.write(":0] in, output bit[");
        writer.write(String.valueOf(outputBitWith - 1));
        writer.write(":0] out);");
        writer.newLine();
    }

    /**
     * Writes the Module Footer
     *
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateModuleFooter() throws IOException {
        writer.write("endmodule\n");
    }

    /**
     * Writes a Variable
     *
     * @param variableName     The name of the variable
     * @param variableBitWidth The Bit Width of this variable
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateVariable(final String variableName, final int variableBitWidth) throws IOException {
        writer.write("\tbit [");
        writer.write(String.valueOf(variableBitWidth - 1));
        writer.write(":0] ");
        writer.write(variableName);
        writer.write(";");
        writer.newLine();

    }

    /**
     * Writes the Posedge Block
     *
     * @param initialState The initial state of the Automata
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generatePosedgeBlock(final State initialState) throws IOException {
        writer.write("\talways_ff @(posedge clk) begin\n\t\tstate <= rst ? ");
        writer.write(String.valueOf(stateEncoding.getWidth()));
        writer.write("'b");
        writer.write(stateEncoding.encode(initialState).toString('?'));
        writer.write(" : nextState;\n");
        writer.write("\t\tout <= nextOut;\n\tend\n");
        writer.newLine();
    }

    /**
     * Writes the Condition Block Header
     *
     * @param inputBitWidth The width of the Input Symbol
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateConditionsHeader(final int inputBitWidth) throws IOException {
        writer.write("\twire [");
        writer.write(String.valueOf(inputBitWidth + stateEncoding.getWidth() - 1));
        writer.write(":0] tmp;");
        writer.newLine();
        writer.write("\tassign tmp = {in, state};\n");
        writer.newLine();
        writer.write("\talways_comb begin\n");
        writer.write("\t\tnextOut = out;\n");
        writer.write("\t\tnextState = state;\n");
        writer.write("\t\tcasez(tmp)\n");
    }

    /**
     * Writes one Condition
     *
     * @param startState The start state of transition
     * @param event      The input event of transition
     * @param endState   The end state of transition
     * @param output     The output of transition
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateCondition(final State startState, final BitField event, final State endState, final BitField output) throws IOException {
        writer.write("\t\t\t"); // indentation

        // TODO
    }

    /**
     * Writes the Condition Footer
     *
     * @throws IOException Can be thrown in case of File Problem
     */
    protected void generateConditionsFooter() throws IOException {
        writer.write("\t\tendcase\n");
        writer.write("\tend\n");
    }

    @Override
    public void export(final Fsm fsm) throws IOException {
        stateEncoding.init(fsm.getNumberOfStates());

        // TODO
    }
}
