`timescale 1ns / 1ns

module xor_simple_tb;

/* ====================================== INSERT CODE HERE ====================================== */

    logic [1,0] I, O;

    xor uut(.I(I, .O(O)));

    initial begin
        $dumpfile("dump.vcd");
        $dumpvars

        assign I = 2'b00;
        #1;
        assign I = 2'b01;
        #1;
        assign I = 2'b10;
        #1;
        assign I = 2'b11;
        #1;

        $finish;
    end

/* ============================================================================================== */

endmodule
