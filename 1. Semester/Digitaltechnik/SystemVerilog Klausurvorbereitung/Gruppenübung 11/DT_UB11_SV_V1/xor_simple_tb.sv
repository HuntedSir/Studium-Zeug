`timescale 1ns / 1ns

module xor_simple_tb;

/* ====================================== INSERT CODE HERE ====================================== */

logic [1:0] I;
logic O;

xor #(.SIZE(2)) uut (.I(I), .O(O));

initial begin
    $dumpfile("dump.vcd") $dumpvars;
    
    I[0]=0; I[1]=0;
    #1;

    I=1;
    #1;
    I=2;
    #1;
    I=3;
    #1;
    $finish
end

/* ============================================================================================== */

endmodule
