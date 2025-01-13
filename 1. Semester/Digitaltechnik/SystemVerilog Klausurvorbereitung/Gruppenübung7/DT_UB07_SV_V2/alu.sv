`timescale 1ns / 1ns

/* ====================================== INSERT CODE HERE ====================================== */

module ALU (
    input logic [31:0] A, [31:0] B, [1:0] OPC,
    output logic [31:0] R
);

always_comb begin : 
    case (OPC)
    2'b00 : R = A + B;
    2'b01 : A <<< B;
    2'b10 : 
    default: R = 32'd0;
end
    
endmodule

/* ============================================================================================== */
