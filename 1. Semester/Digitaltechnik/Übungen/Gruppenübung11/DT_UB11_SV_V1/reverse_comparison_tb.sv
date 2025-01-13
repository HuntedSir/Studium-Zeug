`timescale 1ns / 1ns

module reverse_comparison_tb;

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  logic         EXPECTED_Y2, EXPECTED_Y4;
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic [1:0] A2, B2;
  logic [3:0] A4, B4;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  logic Y2, Y4;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  reverse_comparison #(.N(2)) uut2( .A(A2),
                            .B(B2),
                            .Y(Y2));
  reverse_comparison #(.N(4)) uut4( .A(A4),
                            .B(B4),
                            .Y(Y4));
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("reverse_comparison_tb.vcd");
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;

    $display("************* STARTING SIMULATION *************");

    $display("### Starting test 1: Exhaustive test for N=2 ###");
    for (int i = 0; i < 16; i++) begin
      A2 = i[1:0];
      B2 = i[3:2];
      EXPECTED_Y2 = A2 == {B2[0], B2[1]};
      #1;
      if (Y2 !== EXPECTED_Y2) begin
        $display("ERROR: For inputs %b and %b expected output %b, but got %b",
              A2, B2, EXPECTED_Y2, Y2);
        ERROR = 1'b1;
      end
    end

    $display("### Starting test 2: Exhaustive test for N=4 ###");
    for (int i = 0; i < 256; i++) begin
      A4 = i[3:0];
      B4 = i[7:4];
      EXPECTED_Y4 = A4 == {B4[0], B4[1], B4[2], B4[3]};
      #1;
      if (Y4 !== EXPECTED_Y4) begin
        $display("ERROR: For inputs %b and %b expected output %b, but got %b",
              A4, B4, EXPECTED_Y4, Y4);
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
