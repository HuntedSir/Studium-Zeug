`timescale 1ns / 1ns

module mealy_fsm_tb;

  //****************** SIMULATION PARAMETERS ******************//
localparam    CLK_PERIOD       =    10;  // [ns]
localparam    CLK_PERIOD_HALF = CLK_PERIOD/2;

  //***********************************************************//

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  logic         ok;                     // Flag for single tests
  logic [3:0]  testnum;          // to check where we are in the sim??
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic         CLK;
  logic         RESET;
  logic         E;          // FSM input
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  // FSM output
  logic         Z;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  mealy_fsm uut(
                  .CLK(CLK),
                  .RESET(RESET),
                  .E(E),
                  .Z(Z));
  //***********************************************************//

  //******************* CLOCK SIGNAL *******************//
  always begin
    #(CLK_PERIOD/2) CLK = ~CLK;
  end
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("mealy_fsm_tb.vcd");
    $timeformat(-1, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;
    ok          = 1'b1;
    testnum = 4'b0;

    // Initialize Inputs
    CLK         = 0;
    #(CLK_PERIOD * 0.6);
    RESET       = 1;
    #(CLK_PERIOD);
    RESET       = 0;

    $display("************* STARTING SIMULATION *************");

    #(CLK_PERIOD);

    $display("### Starting test 1: Correct initialization after RESET, no matter the input ###");
    ok = 1'b1;
    RESET = 1;
    testnum = 4'b1;
    E = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkoutput(1'b0, 1'b1);
    #(CLK_PERIOD);
    RESET = 1;
    E = 0;
    #(CLK_PERIOD);
    RESET = 0;
    checkoutput(1'b0, 1'b0);

    if (ok) begin
      $display("success");
    end

    $display("### Starting test 2: Transitions from S0 always output 0 ###");
    ok = 1'b1;
    testnum = 4'd2;
    // transition for E=1 should output Y=0
    reset_fsm();
    E = 1;
    #(CLK_PERIOD);
    checkoutput(1'b0, 1'b1);
    // transition for E=0 should output Y=0
    reset_fsm();
    E = 0;
    #(CLK_PERIOD);
    checkoutput(1'b0, 1'b0);
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 3: Transitions from S1 always output 0 ###");
    ok = 1'b1;
    testnum = 4'd3;
    // S0 -- 1 --> S1 -- 0 --> S4, Z = 0
    reset_fsm();
    E=1; // to S1
    #(CLK_PERIOD);
    E=0; // to S4
    checkoutput(1'b0, 2'b10);
    // S0 -- 1 --> S1 -- 1 --> S2, Z = 0
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    checkoutput(1'b0, 2'b11);
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 4: Transitions from S2 ###");
    ok = 1'b1;
    testnum = 4'd4;
    // S0 -- 1 --> S1 -- 1 --> S2 -- 0 --> S0, Z = 1
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    E=0;
    #(CLK_PERIOD/2);
    checkoutput(1'b1, 1'b0);
    #(CLK_PERIOD/2);
    // S0 -- 1 --> S1 -- 1 --> S2 -- 1 --> S3, Z = 0
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD/2);
    checkoutput(1'b0, 1'b1);
    #(CLK_PERIOD/2);
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 5: Transitions from S3 ###");
    ok = 1'b1;
    testnum = 4'd5;
    // S0 -- 1 --> S1 -- 1 --> S2 -- 1 --> S3 -- 0 --> S4, Z = 0
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    E=0;
    #(CLK_PERIOD/2);
    checkoutput(1'b0, 1'b0);
    #(CLK_PERIOD/2);
    // S0 -- 1 --> S1 -- 1 --> S2 -- 1 --> S3 -- 1 --> S1, Z = 1
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD/2);
    checkoutput(1'b1, 1'b1);
    #(CLK_PERIOD/2);
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 6: Transitions from S4 ###");
    ok = 1'b1;
    testnum = 4'd6;
    // S0 -- 1 --> S1 -- 0 --> S4 -- 0 --> S4, Z = 1
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 0;
    #(CLK_PERIOD);
    E=0;
    #(CLK_PERIOD/2);
    checkoutput(1'b1, 1'b0);
    #(CLK_PERIOD/2);
    // S0 -- 1 --> S1 -- 0 --> S4 -- 1 --> S3, Z = 0
    reset_fsm();
    E=1;
    #(CLK_PERIOD);
    E = 0;
    #(CLK_PERIOD);
    E = 1;
    #(CLK_PERIOD/2);
    checkoutput(1'b0, 1'b1);
    #(CLK_PERIOD/2);
    if (ok) begin
      $display("success");
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
    $display("************* SIMULATION KILLED BECAUSE OF TIMEOUT *************");
    $finish;
  end

  //***********************************************************//

  //********************* SIMULATION TASKS ********************//

task reset_fsm;
    #(CLK_PERIOD);
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
endtask

  task checkoutput(input z, in);
    if (z === Z) begin
    end else begin
      $display("ERROR: FSM output Z set to %b expected %b; for input %b", Z, z, in);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  //***********************************************************//

endmodule
