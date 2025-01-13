`timescale 1ns / 10 ps

module mul4x4
    ( input  logic       CLK,
      input  logic       RST,
      input  logic       START, // Read new inputs and start new calculation on posedge CLK
      input  logic [3:0] A, B,  // Input numbers
      output logic       DONE,  // Will be set to 1 when multiplication finished
      output logic [7:0] Y);    // A * B

/* ===================================== CLEAN UP THIS CODE ===================================== */

  logic [2:0] n;
  logic [3:0] b;
  logic [7:0] a, p;
  
  always_ff @(posedge CLK) begin
    if (RST) begin
      {n, a, b, p, DONE, Y} <= 0;
    end else if (START) begin
      p <= 0; a <= A; b <= B; n <= 4; DONE <= 0;
    end else if (n > 1) begin
      if (b[0]) p <= p + a;
      a <= a << 1; b <= b >> 1; n <= n-1;
    end else if (n == 1) begin
      Y <= b[0] ? p + a : p; n <= 0; DONE <= 1;
    end else begin
      {DONE, Y} <= 0;
    end
  end

/* ============================================================================================== */

endmodule
