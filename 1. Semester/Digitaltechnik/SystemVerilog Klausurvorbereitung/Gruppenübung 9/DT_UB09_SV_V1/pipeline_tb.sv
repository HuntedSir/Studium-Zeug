`timescale 1ns / 10 ps

module pipeline_tb;

  //****************** SIMULATION PARAMETERS ******************//
  localparam    CLK_PERIOD       =    1.0;  // [ns] -> 1 GHz
  //***********************************************************//

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic         CLK;
  logic         SET;
  logic [7:0]   IN;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  logic [7:0]   OUT;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  pipeline uut(
            .CLK(CLK),
            .SET(SET),
            .IN(IN),
            .OUT(OUT));
  //***********************************************************//

  //******************** 1GHz CLOCK SIGNAL ********************//
  always begin
    #(CLK_PERIOD/2) CLK = ~CLK;
  end
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("pipeline_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;

    // Initialize Inputs
    CLK         = 0;
    SET         = 0;
    #(CLK_PERIOD);

    $display("************* STARTING SIMULATION *************");

    $display("### Starting test 1: Result arrives at correct time ###");
    #(CLK_PERIOD / 4);
    SET = 1;
    IN = 2;
    #(3*CLK_PERIOD / 4);
    SET = 0;
    #(CLK_PERIOD / 10); // delay a bit so that we definitely have stable values
    // Now, IN is read
    if (OUT !== 8'bx) begin
      $display("First input should be read now, expected output %b but got %b, too early!", 8'bx, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    // Now, value should have reached second register
    if (OUT !== 8'bx) begin
      $display("First input should be in second register now, expected output %b but got %b, too early!", 8'bx, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    // Now, value should have reached third register
    if (OUT !== 8'bx) begin
      $display("First input should be in third register now, expected output %b but got %b, too early!", 8'bx, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    // Now, value should have reached fourth register
    if (OUT !== 8'bx) begin
      $display("First input should be in fourth register now, expected output %b but got %b, too early!", 8'bx, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    // Now, value should have reached last register
    if (OUT === 8'bx) begin
      $display("First input should be in last register now, expected some defined output but got %b.", OUT);
      ERROR = 1'b1;
      #(CLK_PERIOD);
      // 1 tick too far
      if (OUT !== 8'bx) begin
        $display("Result arrived one clock cycle too late!");
      end else begin
        #(CLK_PERIOD);
        // 2 ticks too far
        if (OUT !== 8'bx) begin
          $display("Result arrived two clock cycles too late!");
        end
      end
    end

    $display("### Starting test 2: Result is correct and stays, even after some time as R1 holds state ###");
    #(10*CLK_PERIOD);
    if (OUT !== 8'd10) begin
      $display("Expected output %d for input %d, but got output %d", 8'd10, IN, OUT);
      ERROR = 1'b1;
    end

    $display("### Starting test 3: Computing another value ###");
    IN = 5;
    SET = 1;
    #(CLK_PERIOD / 2);
    SET = 0;
    #(CLK_PERIOD / 2);
    // Now wait
    #(10*CLK_PERIOD);
    if (OUT !== 8'd52) begin
      $display("Expected output %d for input %d, but got output %d", 8'd52, IN, OUT);
      ERROR = 1'b1;
    end

    $display("### Starting test 4: Pipelining multiple values ###");
    SET = 1;
    IN = 2;
    #(CLK_PERIOD);
    // 2 now in R1
    IN = 3;
    #(CLK_PERIOD);
    // 2 now in R2
    IN = 4;
    #(CLK_PERIOD);
    // 2 now in R3
    IN = 5;
    #(CLK_PERIOD);
    // 2 now in R4
    IN = 6;
    #(CLK_PERIOD);
    // 2 now in R5
    if (OUT !== 8'd10) begin
      $display("Expected output %d for input %d, but got output %d", 8'd10, 8'd2, OUT);
      ERROR = 1'b1;
    end
    IN = 7;
    #(CLK_PERIOD);
    if (OUT !== 8'd20) begin
      $display("Expected output %d for input %d, but got output %d", 8'd20, 8'd3, OUT);
      ERROR = 1'b1;
    end
    IN = 8;
    #(CLK_PERIOD);
    if (OUT !== 8'd34) begin
      $display("Expected output %d for input %d, but got output %d", 8'd34, 8'd4, OUT);
      ERROR = 1'b1;
    end
    IN = 9;
    #(CLK_PERIOD);
    if (OUT !== 8'd52) begin
      $display("Expected output %d for input %d, but got output %d", 8'd52, 8'd5, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    if (OUT !== 8'd74) begin
      $display("Expected output %d for input %d, but got output %d", 8'd74, 8'd6, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    if (OUT !== 8'd100) begin
      $display("Expected output %d for input %d, but got output %d", 8'd100, 8'd7, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    if (OUT !== 8'd130) begin
      $display("Expected output %d for input %d, but got output %d", 8'd130, 8'd8, OUT);
      ERROR = 1'b1;
    end
    #(CLK_PERIOD);
    if (OUT !== 8'd164) begin
      $display("Expected output %d for input %d, but got output %d", 8'd164, 8'd9, OUT);
      ERROR = 1'b1;
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
