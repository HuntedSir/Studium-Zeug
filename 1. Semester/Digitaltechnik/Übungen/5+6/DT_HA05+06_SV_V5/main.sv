' timescale 1 ns / 1 ns

module main

( input logic A ,
 input logic B ,
 input logic C ,
 input logic D ,
 input logic E ,

 output logic Y );

 /* ====================================== INSERT CODE HERE ====================================== */

    assign Y = ~(A & B & C & D & E | ~A & ~B & C & D & ~E | ~A & B & ~C & ~D & E | 
    A & ~B & C & ~D & ~E)

 /* ============================================================================================== */

endmodule
