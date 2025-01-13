`timescale 1ns / 1ns

module reverse_comparison #(parameter N)
                          ( input  logic [N-1:0] A, B,
                            output logic         Y);

/* ====================================== INSERT CODE HERE ====================================== */

logic [N-1:0] O;
genvar i;
generate
  for (i = 0; i < N; i=i+1) begin
    bit_comparison bc (.A(A[i]), .B(B[N - 1 - i]), .O(O[i]));
  end
endgenerate

assign Y = &O;

/* ============================================================================================== */

endmodule

