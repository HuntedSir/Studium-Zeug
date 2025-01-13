`timescale 1ns / 1ns

module multiplexer_tb;

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR1, ERROR2;              // Will be 1 if any test failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic [7:0] m0, m1, n0, n1, n2, n3;
  logic       s;
  logic [1:0] t;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  logic [7:0] x, y;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  multiplexer1bit uut1( .m0(m0),
                        .m1(m1),
                        .s(s),
                        .out(x));

  multiplexer2bit uut2( .m0(n0),
                        .m1(n1),
                        .m2(n2),
                        .m3(n3),
                        .s(t),
                        .out(y));
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("multiplexer_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR1       = 1'b0;

    $display("************* STARTING SIMULATION FOR multiplexer1bit  *************");

    // exhaustive test ignoring first two bits due to time reasons
    for (int i = 0; i < 64; i++) begin
			for (int j = 0; j < 64; j++) begin
				for (int k = 0; k < 2; k++) begin
					m0 = i;
					m1 = j;
					s = k;
					#1
					case (s)
            1'b0: if (x !== i) begin
                    $display("Test for s %b, m0 %b and m1 %b: Expected out %b, but was %b", s, m0, m1, m0, x);
                    ERROR1 = 1'b1;
                  end
            1'b1: if (x !== j) begin
                      $display("Test for s %b, m0 %b and m1 %b: Expected out %b, but was %b", s, m0, m1, m1, x);
                      ERROR1 = 1'b1;
                  end
          endcase
				end
			end
		end
    // Test with all ones for completeness:
    m0 = 8'hFF;
		m1 = 8'hFF;
		s = 1'b1;
		#1
    if (x !== m1) begin
      $display("Test for s %b, m0 %b and m1 %b: Expected out %b, but was %b", s, m0, m1, m1, x);
      ERROR1 = 1'b1;
    end
    m0 = 8'hFF;
		m1 = 8'hFF;
		s = 1'b0;
		#1
    if (x !== m0) begin
      $display("Test for s %b, m0 %b and m1 %b: Expected out %b, but was %b", s, m0, m1, m0, x);
      ERROR1 = 1'b1;
    end

    $display("************* SIMULATION COMPLETE FOR multiplexer1bit *************");  
    if (ERROR1 == 0) begin
      $display("All tests succeeded");
    end else begin
      $display("THERE WERE ERRORS");
      $display("Please fix errors before proceeding with multiplexer2bit");
      $finish;
    end

    // Initialize Additional Testing Values
    ERROR2       = 1'b0;

    $display("************* STARTING SIMULATION FOR multiplexer2bit  *************");

    // exhaustive test with only 2 bits due to time reasons
    for (int i = 0; i < 4; i++) begin
      for (int j = 0; j < 4; j++) begin
        for (int k = 0; k < 4; k++) begin
          for (int l = 0; l < 4; l++) begin
            for (int m = 0; m < 4; m++) begin
              n0 = i;
              n1 = j;
              n2 = k;
              n3 = l;
              t = m;
              #1
              case (t)
                  2'b00: if (y !== i) begin
                      $display("Test for s %b, m0 %b, m1 %b, m2 %b and m3 %b: Expected %b, but was %b", t, n0, n1, n2, n3, n0, y);
                      ERROR2 = 1'b1;
                  end
                  2'b01: if (y !== j) begin
                      $display("Test for s %b, m0 %b, m1 %b, m2 %b and m3 %b: Expected %b, but was %b", t, n0, n1, n2, n3, n1, y);
                      ERROR2 = 1'b1;
                  end
                  2'b10: if (y !== k) begin
                      $display("Test for s %b, m0 %b, m1 %b, m2 %b and m3 %b: Expected %b, but was %b", t, n0, n1, n2, n3, n2, y);
                      ERROR2 = 1'b1;
                  end
                  2'b11: if (y !== l) begin
                      $display("Test for s %b, m0 %b, m1 %b, m2 %b and m3 %b: Expected %b, but was %b", t, n0, n1, n2, n3, n3, y);
                      ERROR2 = 1'b1;
                  end
              endcase
            end
          end
        end
      end
    end
    // Test with all ones for completeness:
    n0 = 8'hFF;
		n1 = 8'hFF;
		n2 = 8'hFF;
		n3 = 8'hFF;
		t = 2'b11;
		#1
    if (y !== n3) begin
      $display("Test for s %b, m0 %b, m1 %b, m2 %b and m3 %b: Expected %b, but was %b", t, n0, n1, n2, n3, n3, y);
      ERROR2 = 1'b1;
    end

    $display("************* SIMULATION COMPLETE FOR multiplexer2bit *************");  
    if (ERROR2 == 0) begin
      $display("All tests succeeded");
    end else begin
      $display("THERE WERE ERRORS");
    end

    $display("************* FINAL RESULTS *************");  
    if (ERROR1 == 0) begin
      $display("All tests for multiplexer1bit succeeded");
    end else begin
      $display("THERE WERE ERRORS FOR multiplexer1bit");
    end
    if (ERROR2 == 0) begin
      $display("All tests for multiplexer2bit succeeded");
    end else begin
      $display("THERE WERE ERRORS FOR multiplexer2bit");
    end
    $finish;
  end

  //***********************************************************//
endmodule
