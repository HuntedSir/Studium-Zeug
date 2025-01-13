`timescale 1ns / 1ns

module display

    ( input  logic  [3: 0]  DIGIT,    // Binary encoding of the digit to display

      output logic  [15:0]  SEGMENT); // Encoding of all segments to enable

/* ====================================== INSERT CODE HERE ====================================== */

always_comb case(DIGIT)
                 abcd_ef(g1)(g2)_hijk_lm(dp)(dk)
0: SEGMENT = 16'b1111_1100_0011_0000;
1: SEGMENT = 16'b0110_0000_0100_0000;
2: SEGMENT = 16'b1110_1011_0000_0000;
3: SEGMENT = 16'b1111_0011_0000_0000;
4: SEGMENT = 16'b0110_0111_0000_0000;
5: SEGMENT = 16'b1011_0111_0000_0000;
6: SEGMENT = 16'b1011_1111_0000_0000;
7: SEGMENT = 16'b1110_0000_0000_0000;
8: SEGMENT = 16'b1111_1111_0000_0000;
9: SEGMENT = 16'b1111_0111_0000_0000;
default: SEGMENT = 16'b0000_0000_1011_0100;


/* ============================================================================================== */

endmodule
