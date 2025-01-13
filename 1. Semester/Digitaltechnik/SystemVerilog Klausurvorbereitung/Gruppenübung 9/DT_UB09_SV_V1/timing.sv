`timescale 1 ns / 10 ps

module timing(input  logic        CLK, R,
              input  logic        X, Y,
              output logic        A, B, C, D, E, F, G, H,
              output logic [15:0] I, J);
  
  assign A = ~X;
  always @(posedge CLK) begin
    B = X;
    C = B;
  end
  always @(CLK) begin
    #1;
    D = CLK;
  end
  always @(negedge D) begin
    E <= A;
    F <= E;
  end
  always @(CLK & D) G = Y;
  always_comb H = Y & G;
  always_ff @(negedge R) I = 1;
  always @(posedge CLK) begin
    I <= I << 1;
    J <= I - 1;
  end

endmodule
