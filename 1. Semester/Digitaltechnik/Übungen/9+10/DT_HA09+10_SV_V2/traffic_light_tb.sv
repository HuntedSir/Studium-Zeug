`timescale 1ns / 1ns

module traffic_light_tb;

  //****************** SIMULATION PARAMETERS ******************//
  localparam    CLK_PERIOD       =    50;  // [ns] -> 20 MHz
  localparam    RED_GREEN_LENGTH =  4000;  // CLK cycles for one road having green and one red
  localparam    YELLOW_LENGTH    =   500;  // CLK cycles for both roads having yellow
  //***********************************************************//

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  logic         ok;                 // Flag for single tests
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic         CLK;
  logic         RESET;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  // X_ for road X, Y_ for road Y
  logic         X_RED;
  logic         Y_RED;
  logic         X_YELLOW;
  logic         Y_YELLOW;
  logic         X_GREEN;
  logic         Y_GREEN;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  traffic_light uut(
                  .CLK(CLK),
                  .RESET(RESET),
                  .X_RED(X_RED),
                  .Y_RED(Y_RED),
                  .X_YELLOW(X_YELLOW),
                  .Y_YELLOW(Y_YELLOW),
                  .X_GREEN(X_GREEN),
                  .Y_GREEN(Y_GREEN));
  //***********************************************************//

  //******************* 20 MHz CLOCK SIGNAL *******************//
  always begin
    #(CLK_PERIOD/2) CLK = ~CLK;
  end
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("traffic_light_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;
    ok          = 1'b1;

    // Initialize Inputs
    CLK         = 0;
    RESET       = 1;
    #(CLK_PERIOD);
    RESET       = 0;

    $display("************* STARTING SIMULATION *************");

    #(CLK_PERIOD);

    $display("### Starting test 1: Correct initialization after RESET ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    #(CLK_PERIOD);
    checkcorrect(3'b001, 3'b100, "begin of green for X");
    if (ok) begin
      $display("success");
    end

    // Use some border region of 40 clock cycles between transitions to compensate for off-by-1
    // errors for the counter that we tolerate.

    $display("### Starting test 2: Green Lights ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkgreen(1'b1, 1'b0, "begin of green for X");
    #(100*CLK_PERIOD);
    checkgreen(1'b1, 1'b0, "begin of green for X");
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkgreen(1'b1, 1'b0, "end of green for X");
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      checkgreen(1'b0, 1'b0, "begin of yellow for X");
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b0, 1'b0, "end of yellow for X");
      #(40*CLK_PERIOD); // => Y
      checkgreen(1'b0, 1'b1, "begin of green for Y");
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b0, 1'b1, "end of green for Y");
      #(40*CLK_PERIOD); // => Y to X
      checkgreen(1'b0, 1'b0, "begin of yellow for Y");
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b0, 1'b0, "end of yellow for Y");
      #(40*CLK_PERIOD); // => X
      checkgreen(1'b1, 1'b0, "begin of green for X");
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b1, 1'b0, "end of green for X");
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 3: Yellow Lights ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkyellow(1'b0, 1'b0, "begin of green for X");
    #(100*CLK_PERIOD);
    checkyellow(1'b0, 1'b0, "begin of green for X");
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkyellow(1'b0, 1'b0, "end of green for X");
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      checkyellow(1'b1, 1'b1, "begin of yellow for X");
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1, "end of yellow for X");
      #(40*CLK_PERIOD); // => Y
      checkyellow(1'b0, 1'b0, "begin of green for Y");
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b0, 1'b0, "end of green for Y");
      #(40*CLK_PERIOD); // => Y to X
      checkyellow(1'b1, 1'b1, "begin of yellow for Y");
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1, "end of yellow for Y");
      #(40*CLK_PERIOD); // => X
      checkyellow(1'b0, 1'b0, "begin of green for X");
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b0, 1'b0, "end of green for X");
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 4: Red Lights standalone (no red-yellow) ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkred(1'b0, 1'b1, "begin of green for X");
    #(100*CLK_PERIOD);
    checkred(1'b0, 1'b1, "begin of green for X");
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkred(1'b0, 1'b1, "end of green for X");
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      #(40*CLK_PERIOD); // => Y
      checkred(1'b1, 1'b0, "begin of green for Y");
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkred(1'b1, 1'b0, "end of green for Y");
      #(40*CLK_PERIOD); // => Y to X
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      #(40*CLK_PERIOD); // => X
      checkred(1'b0, 1'b1, "begin of green for X");
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkred(1'b0, 1'b1, "end of green for X");
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 5: Red-Yellow vs Yellow ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    #(100*CLK_PERIOD);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      checkyellow(1'b1, 1'b1, "begin of yellow for X");
      checkred(1'b0, 1'b1, "begin of yellow for X");
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1, "end of yellow for X");
      checkred(1'b0, 1'b1, "end of yellow for X");
      #(40*CLK_PERIOD); // => Y
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      #(40*CLK_PERIOD); // => Y to X
      checkyellow(1'b1, 1'b1, "begin of yellow for Y");
      checkred(1'b1, 1'b0, "begin of yellow for Y");
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1, "end of yellow for Y");
      checkred(1'b1, 1'b0, "end of yellow for Y");
      #(40*CLK_PERIOD); // => X
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
    end
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
    $display("************* SIMULATION KILLED BECAUSE OF ERROR *************");
    $finish;
  end

  //***********************************************************//

  //********************* SIMULATION TASKS ********************//
  task checkcorrect (input [2:0] x, y, input string msg);
    if ((x === {X_RED, X_YELLOW, X_GREEN}) && (y === {Y_RED, Y_YELLOW, Y_GREEN})) begin
    end else begin
      $display("ERROR: traffic light X set to %b (ryg), expected %b; traffic light Y set to %b, expected %b, at %s", {X_RED, X_YELLOW, X_GREEN}, x, {Y_RED, Y_YELLOW, Y_GREEN}, y, msg);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task checkgreen(input x, y, input string msg);
    if ((x === X_GREEN) && (y === Y_GREEN)) begin
    end else begin
      $display("ERROR: green of traffic light X set to %b expected %b; green of traffic light Y set to %b, expected %b, at %s", X_GREEN, x, Y_GREEN, y, msg);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task checkyellow(input x, y, input string msg);
    if ((x === X_YELLOW) && (y === Y_YELLOW)) begin
    end else begin
      $display("ERROR: yellow of traffic light X set to %b expected %b; yellow of traffic light Y set to %b, expected %b, at %s", X_YELLOW, x, Y_YELLOW, y, msg);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task checkred(input x, y, input string msg);
    if ((x === X_RED) && (y === Y_RED)) begin
    end else begin
      $display("ERROR: red of traffic light X set to %b expected %b; red of traffic light Y set to %b, expected %b, at %s", X_RED, x, Y_RED, y, msg);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask
  //***********************************************************//

endmodule
