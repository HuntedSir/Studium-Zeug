`timescale 1ns / 1ns

module multiplier_tb;

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  logic [7:0]   EXPECTED_PRODUCT;
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic [3:0] a, b;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
	logic [7:0] product;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  multiplier uut( .a(a),
  				        .b(b),
				          .product(product));
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("multiplier_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;

    $display("************* STARTING SIMULATION *************");

    // exhaustive test
    for (int i = 0; i < 16; i++) begin
			for (int j = 0; j < 16; j++) begin
				a = i;
				b = j;
        EXPECTED_PRODUCT = i * j;
				#1
				if (product !== EXPECTED_PRODUCT) begin
					$display("Test for a %b and b %b: Expected product %b, but was %b", a, b, EXPECTED_PRODUCT, product);
          ERROR = 1'b1;
				end
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
