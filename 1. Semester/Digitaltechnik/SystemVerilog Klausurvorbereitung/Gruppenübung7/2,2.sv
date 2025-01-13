module m3 (
    input logic [2:0] A,
    output logic Y
);

assign Y = (A[0] | A[2]) ~^ A[1]; 
    
endmodule

module m3 (
    input logic [3:0] A, [1:0] B,
    output logic Y
);

assign Y[0] = (A[0] ^ A[1]) ^ (A[2] ^ A[3]) ^ B[0]

assign Y[1] = (A[0] ^ A[1]) ^ (A[2] ^ A[3]) ^ B[1]
    
endmodule