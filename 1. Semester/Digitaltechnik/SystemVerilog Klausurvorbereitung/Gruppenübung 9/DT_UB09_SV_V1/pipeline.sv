`timescale 1ns / 10 ps

module pipeline
    ( input  logic       CLK,
      input  logic       SET,  // If set, first register should read input when posedge CLK
      input  logic [7:0] IN,   // Input for first register (if SET=1)
      output logic [7:0] OUT); // Output after last register

  // Registers (each containing the currently saved and output value)
  logic [7:0] register1, register2, register3, register4, register5; 
  
  // Outputs of the combinatorial blocks between registers, act as inputs to next register
  // index i for output of the block that comes after register i.
  logic [7:0] out1, out2 , out3 , out4;

/* ====================================== INSERT CODE HERE ====================================== */

always_ff @( posedge CLK ) begin
  if (set) begin
    register1 = in;
  end
end
  
always_ff @( posedge CLK ) begin
  register2 <= out1;
  register3 <= out2;
  register4 <= out3;
  register5 <= out4;
end

stage1 s1 (register1, out1);
stage1 s2 (register2, out2);
stage1 s3 (register3, out3);
stage1 s4 (register4, out4);

assign OUT = register5;
/* ============================================================================================== */

endmodule

// Modules for single pipeline steps / combinatorial blocks:

/* ====================================== INSERT CODE HERE ====================================== */

module stage1 (
  input logic [7:0] in,
  output logic [7:0] out
);

assign out = in * in;
  
endmodule

module stage2 (
  input logic [7:0] in,
  output logic [7:0] out
);

assign out = in + 5;
  
endmodule

module stage3 (
  input logic [7:0] in,
  output logic [7:0] out
);

assign out = in * 2;
  
endmodule

module stage4 (
  input logic [7:0] in,
  output logic [7:0] out
);

assign out = in - 8;
  
endmodule

/* ============================================================================================== */
