`timescale 1ns / 1ns

module bit_comparison_tb;

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic A, B;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  logic Y;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  bit_comparison uut( .A(A),
  				            .B(B),
				              .Y(Y));
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("bit_comparison_tb.vcd");
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;

    $display("************* STARTING SIMULATION *************");

    // exhaustive test
    for (int i = 0; i < 4; i++) begin
      A = i[0];
      B = i[1];
			#1;
			if (Y !== ~^i) begin
				$display("ERROR: For inputs %b and %b expected output %b, but got %b", A, B, ~^i, Y);
          ERROR = 1'b1;
			end
		end

    $display("************* SIMULATION COMPLETE *************");  
    if (ERROR == 0) begin
      $display("All tests succeeded");
    end else begin
      $display("THERE WERE ERRORS");
    end
    $finish;
  end

  //***********************************************************//
endmodule
