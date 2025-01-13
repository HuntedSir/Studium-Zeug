`timescale 1ns / 1ns

module display_tb;

  //****************** SIMULATION PARAMETERS ******************//
  localparam    CLK_PERIOD  = 50; // [ns] -> 20 MHz
  //***********************************************************//

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;            // Will be 1 if any test failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic         CLK;
  logic [3  :0] DIGIT;
  //***********************************************************//


  //********************* MODULE OUTPUTS **********************//
  wire  [15 :0] SEGMENT;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  display uut (.DIGIT(DIGIT), .SEGMENT(SEGMENT));
  //***********************************************************//

  //******************* 20 MHz CLOCK SIGNAL *******************//
  always begin
    #(CLK_PERIOD/2) CLK = ~CLK;
  end
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("display_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR   = 1'b0;

    // Initialize Inputs
    CLK     = 0;
    DIGIT   = 0;

    $display("************* STARTING SIMULATION *************");

    #(CLK_PERIOD);

    check(0, 16'b1111110000110000);
    check(1, 16'b0110000000100000);
    check(8, 16'b1111111100000000);
    check_invalid(10);
    #(CLK_PERIOD);

    $display("************* SIMULATION COMPLETE *************");

    if (ERROR == 0) begin
      $display("All tests succeeded");
    end else begin
      $display("THERE WERE ERRORS");
    end

    $finish;
  end

  initial begin
    #(100000*CLK_PERIOD)
    $display("************* SIMULATION KILLED BECAUSE OF ERROR *************");
    $finish;
  end

  //***********************************************************//

  //********************* SIMULATION TASKS ********************//
  task check(input [3:0]  digit, input [15:0] expected_segment);
    DIGIT = digit;
    #(CLK_PERIOD)
    if (SEGMENT == expected_segment) begin
      $display("Test for digit %d:           success", digit);
    end else begin
      $display("Test for digit %d:           ERROR, expected %b, got %b", digit, expected_segment, SEGMENT);
      ERROR = 1'b1;
    end
  endtask

  task check_invalid(input [3:0]  val);
    DIGIT = val;
    #(CLK_PERIOD)
    if (SEGMENT == 16'b0000000010110100) begin
      $display("Test for invalid digit %b: success", val);
    end else begin
      $display("Test for invalid digit %b: ERROR, expected %b, got %b", val, 16'b0000000010110100, SEGMENT);
      ERROR = 1'b1;
    end
  endtask
  //***********************************************************//

endmodule
