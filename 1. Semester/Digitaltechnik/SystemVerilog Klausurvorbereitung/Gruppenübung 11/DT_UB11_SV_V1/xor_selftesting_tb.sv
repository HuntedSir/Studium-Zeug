`timescale 1ns / 1ns

module xor_selftesting_tb;

/* ====================================== INSERT CODE HERE ====================================== */

logic [3:0] I;
logic O;

xor #(.SIZE(4)) uut (.I(I), .O(O));

initial begin
    $dumpfile("dump.vcd") $dumpvars;
    
    for (int i = 0; i < 16 ; i = i+1) begin
        I = i;
        #1
        if(O !== ^I) begin
            $display("Error");
        end else begin
            $display("Ok");
        end
    end

    $finish
end

/* ============================================================================================== */

endmodule
