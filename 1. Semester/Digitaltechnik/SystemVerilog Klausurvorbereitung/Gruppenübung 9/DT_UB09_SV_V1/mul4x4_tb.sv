`timescale 1ns / 10 ps

module mul4x4_tb;

  //****************** SIMULATION PARAMETERS ******************//
  localparam    CLK_PERIOD       =    1.0;  // [ns] -> 1 GHz
  //***********************************************************//

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic         CLK;
  logic         RST;
  logic         START;
  logic [3:0]   A, B;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  logic         DONE;
  logic [7:0]   Y;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  mul4x4 uut(
            .CLK(CLK),
            .RST(RST),
            .START(START),
            .A(A),
            .B(B),
            .DONE(DONE),
            .Y(Y));
  //***********************************************************//

  //******************** 1GHz CLOCK SIGNAL ********************//
  always begin
    #(CLK_PERIOD/2) CLK = ~CLK;
  end
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("mul4x4_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;

    // Initialize Inputs
    CLK         = 0;
    RST         = 0;
    START       = 0;
    #(3*CLK_PERIOD/4);
    RST         = 1;
    #(CLK_PERIOD);
    RST         = 0;
    #(CLK_PERIOD);

    $display("************* STARTING SIMULATION *************");

    $display("### Starting test 1: 1 * 1 ###");
    A     = 4'd1;
    B     = 4'd1;
    START = 1;
    #(CLK_PERIOD);
    START = 0;
    while (DONE === 1'b0) begin
       #(CLK_PERIOD);
    end
    if (Y !== 8'd1) begin
      ERROR       = 1'b1;
      $display("Error: Expected output %d = %d * %d, but got output %d", 1, A, B, Y);
    end
    #(CLK_PERIOD);
    if (DONE === 1'b1) begin
      ERROR       = 1'b1;
      $display("Error: DONE was set too long after finishing");
    end
    #(CLK_PERIOD);

    $display("### Starting test 2: 5 * 4 ###");
    A     = 4'd5;
    B     = 4'd4;
    START = 1;
    #(CLK_PERIOD);
    START = 0;
    while (DONE === 1'b0) begin
       #(CLK_PERIOD);
    end
    if (Y !== 8'd20) begin
      ERROR       = 1'b1;
      $display("Error: Expected output %d = %d * %d, but got output %d", 20, A, B, Y);
    end
    #(CLK_PERIOD);
    if (DONE === 1'b1) begin
      ERROR       = 1'b1;
      $display("Error: DONE was set too long after finishing");
    end
    #(CLK_PERIOD);

    $display("### Starting test 3: 10 * 10, also testing exact timing ###");
    A     = 4'd10;
    B     = 4'd10;
    START = 1;
    #(CLK_PERIOD);
    START = 0;
    #(3*CLK_PERIOD);
    if (DONE === 1'b1) begin
      ERROR       = 1'b1;
      $display("Error: DONE was set too early");
    end
    #(CLK_PERIOD);
    if (Y !== 8'd100) begin
      ERROR       = 1'b1;
      $display("Error: Expected output %d = %d * %d, but got output %d", 100, A, B, Y);
    end
    if (DONE === 1'b0) begin
      ERROR       = 1'b1;
      $display("Error: DONE was not set when it should have been");
    end
    #(CLK_PERIOD);
    if (DONE === 1'b1) begin
      ERROR       = 1'b1;
      $display("Error: DONE was set too long after finishing");
    end

    $display("************* SIMULATION COMPLETE *************");

    if (ERROR == 0) begin
      $display("All tests succeeded");
    end else begin
      $display("THERE WERE ERRORS");
    end

    $finish;
  end

  initial begin
    #(1000000*CLK_PERIOD)
    $display("************* SIMULATION KILLED BECAUSE OF ERROR *************");
    $finish;
  end

  //***********************************************************//

endmodule
