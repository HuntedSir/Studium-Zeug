module foo(
  input  logic [15:0] a,
  input  logic [15:0] b,
  output logic [15:0] x,
  output logic        y
);
  //1. Funktion
  assign x = ((a<<<3) + {b[13:0],2'b0} - (a<<<2) + b)<<<1;

  //2. Funktion
  assign y = |(a + b ^ 16'hCAFE);
endmodule
