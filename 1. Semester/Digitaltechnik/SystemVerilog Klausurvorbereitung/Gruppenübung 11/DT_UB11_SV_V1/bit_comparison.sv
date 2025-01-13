`timescale 1ns / 1ns

module bit_comparison (input  logic A, B,
                       output logic Y);

/* ====================================== INSERT CODE HERE ====================================== */

assign Y = A ~^ B;

/* ============================================================================================== */

endmodule
