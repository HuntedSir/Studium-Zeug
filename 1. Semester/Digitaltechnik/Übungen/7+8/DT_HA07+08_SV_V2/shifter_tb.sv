`timescale 1ns / 1ns

module shifter_tb;

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic [3:0] in;
	logic [1:0] s;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
	logic [7:0] out;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  shifter uut(  .in(in),
  				      .s(s),
				        .out(out));
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("shifter_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;

    $display("************* STARTING SIMULATION *************");

    // exhaustive test
    for (int i = 0; i < 16; i++) begin
			for (int j = 0; j < 4; j++) begin
				in = i;
				s = j;
				#1
				if (out !== i << j) begin
					$display("Test for in %b and s %b: Expected out %b, but was %b", in, s, {4'b0000, in} << s, out);
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
