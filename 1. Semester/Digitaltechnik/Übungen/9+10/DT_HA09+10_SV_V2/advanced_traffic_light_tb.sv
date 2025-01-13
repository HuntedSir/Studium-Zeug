`timescale 1ns / 1ns

module advanced_traffic_light_tb;

  //****************** SIMULATION PARAMETERS ******************//
  localparam    CLK_PERIOD       =    50;  // [ns] -> 20 MHz
  localparam    RED_GREEN_LENGTH =  4000;  // CLK cycles for one road having green and one red
  localparam    YELLOW_LENGTH    =   500;  // CLK cycles for both roads having yellow
  localparam BLINKING_LENGTH     =    50;  // CLK cycles that yellow is on and off each during
                                           // blinking in night mode
  //***********************************************************//

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  logic         ok;                 // Flag for single tests
  integer       LOOP_COUNTER;       // Counter for CLK cycles to time start of phases
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic         CLK;
  logic         RESET;
  logic         NIGHT;
  logic         OFF;
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
  advanced_traffic_light uut(
                                .CLK(CLK),
                                .RESET(RESET),
                                .NIGHT(NIGHT),
                                .OFF(OFF),
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
    $dumpfile("advanced_traffic_light_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;
    ok          = 1'b1;

    // Initialize Inputs
    CLK         = 0;
    RESET       = 1;
    NIGHT       = 0;
    OFF = 0;
    #(3*CLK_PERIOD/2);
    RESET       = 0;

    $display("************* STARTING SIMULATION *************");

    #(CLK_PERIOD);

    $display("### Starting test 1: Correct initialization after RESET ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkcorrect(3'b001, 3'b100);
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
    checkgreen(1'b1, 1'b0);
    #(100*CLK_PERIOD);
    checkgreen(1'b1, 1'b0);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkgreen(1'b1, 1'b0);
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      checkgreen(1'b0, 1'b0);
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b0, 1'b0);
      #(40*CLK_PERIOD); // => Y
      checkgreen(1'b0, 1'b1);
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b0, 1'b1);
      #(40*CLK_PERIOD); // => Y to X
      checkgreen(1'b0, 1'b0);
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b0, 1'b0);
      #(40*CLK_PERIOD); // => X
      checkgreen(1'b1, 1'b0);
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkgreen(1'b1, 1'b0);
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 3: Yellow Lights ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkyellow(1'b0, 1'b0);
    #(100*CLK_PERIOD);
    checkyellow(1'b0, 1'b0);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkyellow(1'b0, 1'b0);
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      checkyellow(1'b1, 1'b1);
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1);
      #(40*CLK_PERIOD); // => Y
      checkyellow(1'b0, 1'b0);
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b0, 1'b0);
      #(40*CLK_PERIOD); // => Y to X
      checkyellow(1'b1, 1'b1);
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1);
      #(40*CLK_PERIOD); // => X
      checkyellow(1'b0, 1'b0);
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b0, 1'b0);
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 4: Red Lights standalone (no red-yellow) ###");
    ok = 1'b1;
    RESET = 1;
    #(CLK_PERIOD);
    RESET = 0;
    checkred(1'b0, 1'b1);
    #(100*CLK_PERIOD);
    checkred(1'b0, 1'b1);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkred(1'b0, 1'b1);
    for (int i = 0; i < 3; i = i + 1) begin
      #(40*CLK_PERIOD); // => X to Y
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      #(40*CLK_PERIOD); // => Y
      checkred(1'b1, 1'b0);
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkred(1'b1, 1'b0);
      #(40*CLK_PERIOD); // => Y to X
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      #(40*CLK_PERIOD); // => X
      checkred(1'b0, 1'b1);
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      checkred(1'b0, 1'b1);
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
      checkyellow(1'b1, 1'b1);
      checkred(1'b0, 1'b1);
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1);
      checkred(1'b0, 1'b1);
      #(40*CLK_PERIOD); // => Y
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
      #(40*CLK_PERIOD); // => Y to X
      checkyellow(1'b1, 1'b1);
      checkred(1'b1, 1'b0);
      #((YELLOW_LENGTH-40)*CLK_PERIOD);
      checkyellow(1'b1, 1'b1);
      checkred(1'b1, 1'b0);
      #(40*CLK_PERIOD); // => X
      #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 7: Blinking in night mode ###");
    ok = 1'b1;
    RESET = 1;
    NIGHT = 1;
    #(CLK_PERIOD);
    RESET = 0;
    testblinking;
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 8: Keeping light off ###");
    ok = 1'b1;
    RESET = 1;
    NIGHT = 0;
    OFF = 1;
    #(CLK_PERIOD);
    RESET = 0;
    testoff;
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 9: Entering night mode ###");
    ok = 1'b1;
    RESET = 1;
    NIGHT = 0;
    OFF = 0;
    #(CLK_PERIOD);
    RESET = 0;
    checkcorrect(3'b001, 3'b100);
    #(100*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    fullcycle;
    halfcycle;
    #(CLK_PERIOD);
    $display("Entering night mode now!");
    NIGHT = 1;
    #(CLK_PERIOD);
    testblinking;
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 10: Switching Off ###");
    ok = 1'b1;
    RESET = 1;
    NIGHT = 0;
    OFF = 0;
    #(CLK_PERIOD);
    RESET = 0;
    checkcorrect(3'b001, 3'b100);
    #(100*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    fullcycle;
    halfcycle;
    #(CLK_PERIOD);
    $display("Entering night mode now!");
    OFF = 1;
    #(CLK_PERIOD);
    testoff;
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 11: Leaving night mode ###");
    ok = 1'b1;
    RESET = 1;
    NIGHT = 1;
    OFF = 0;
    #(CLK_PERIOD);
    RESET = 0;
    testblinking;
    #(BLINKING_LENGTH * CLK_PERIOD / 4);
    $display("Leaving night mode now!");
    NIGHT = 0;
    #(CLK_PERIOD);
    waitforxturnstart;
    #(100*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    for (int i = 0; i < 5; i = i + 1) begin
      fullcycle;
    end
    if (ok) begin
      $display("success");
    end

    $display("### Starting test 12: Switching on ###");
    ok = 1'b1;
    RESET = 1;
    NIGHT = 0;
    OFF = 1;
    #(CLK_PERIOD);
    RESET = 0;
    testoff;
    $display("Switching on now!");
    OFF = 0;
    #(CLK_PERIOD);
    waitforxturnstart;
    #(100*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    #((RED_GREEN_LENGTH-100-20)*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
    for (int i = 0; i < 5; i = i + 1) begin
      fullcycle;
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
    #(3000000*CLK_PERIOD)
    $display("************* SIMULATION KILLED BECAUSE OF ERROR *************");
    $finish;
  end

  //***********************************************************//

  //********************* SIMULATION TASKS ********************//
  task checkcorrect (input [2:0] x, y);
    if ((x === {X_RED, X_YELLOW, X_GREEN}) && (y === {Y_RED, Y_YELLOW, Y_GREEN})) begin
    end else begin
      $display("ERROR: traffic light X set to %b (ryg), expected %b; traffic light Y set to %b, expected %b", {X_RED, X_YELLOW, X_GREEN}, x, {Y_RED, Y_YELLOW, Y_GREEN}, y);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task checkgreen (input x, y);
    if ((x === X_GREEN) && (y === Y_GREEN)) begin
    end else begin
      $display("ERROR: green of traffic light X set to %b expected %b; green of traffic light Y set to %b, expected %b", X_GREEN, x, Y_GREEN, y);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task checkyellow(input x, y);
    if ((x === X_YELLOW) && (y === Y_YELLOW)) begin
    end else begin
      $display("ERROR: yellow of traffic light X set to %b expected %b; yellow of traffic light Y set to %b, expected %b", X_YELLOW, x, Y_YELLOW, y);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task checkred(input x, y);
    if ((x === X_RED) && (y === Y_RED)) begin
    end else begin
      $display("ERROR: red of traffic light X set to %b expected %b; red of traffic light Y set to %b, expected %b", X_RED, x, Y_RED, y);
      ERROR = 1'b1;
      ok = 1'b0;
    end
  endtask

  task waitforblinkingstart;
    LOOP_COUNTER = 0;
    while (X_YELLOW && (LOOP_COUNTER < BLINKING_LENGTH + 3)) begin
      LOOP_COUNTER += 1;
      #(CLK_PERIOD);
    end
    // blinking off or too long
    LOOP_COUNTER = 0;
    while ((~X_YELLOW) && (LOOP_COUNTER < BLINKING_LENGTH + 3)) begin
      LOOP_COUNTER += 1;
      #(CLK_PERIOD);
    end
    // blinking just turned on or too long
  endtask

  task waitforxturnstart;
    LOOP_COUNTER = 0;
    while (X_GREEN && (LOOP_COUNTER < RED_GREEN_LENGTH + 3)) begin
      LOOP_COUNTER += 1;
      #(CLK_PERIOD);
    end
    // left X_GREEN, now goes yellow, red, yellow before entering green again
    LOOP_COUNTER = 0;
    while ((~X_GREEN) && (LOOP_COUNTER < 2 * YELLOW_LENGTH + RED_GREEN_LENGTH + 3)) begin
      LOOP_COUNTER += 1;
      #(CLK_PERIOD);
    end
    // X_GREEN just turned on or too long
  endtask

  task fullcycle;
    #(40*CLK_PERIOD); // => X to Y
    checkcorrect(3'b010, 3'b110);
    #((YELLOW_LENGTH-40)*CLK_PERIOD);
    checkcorrect(3'b010, 3'b110);
    #(40*CLK_PERIOD); // => Y
    checkcorrect(3'b100, 3'b001);
    #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
    checkcorrect(3'b100, 3'b001);
    #(40*CLK_PERIOD); // => Y to X
    checkcorrect(3'b110, 3'b010);
    #((YELLOW_LENGTH-40)*CLK_PERIOD);
    checkcorrect(3'b110, 3'b010);
    #(40*CLK_PERIOD); // => X
    checkcorrect(3'b001, 3'b100);
    #((RED_GREEN_LENGTH-40)*CLK_PERIOD);
    checkcorrect(3'b001, 3'b100);
  endtask

  task halfcycle;
    #(40*CLK_PERIOD); // => X to Y
    checkcorrect(3'b010, 3'b110);
    #((YELLOW_LENGTH-40)*CLK_PERIOD);
    checkcorrect(3'b010, 3'b110);
    #(40*CLK_PERIOD); // => Y
    checkcorrect(3'b100, 3'b001);
  endtask

  task testblinking;
    waitforblinkingstart;
    #(BLINKING_LENGTH * CLK_PERIOD / 2);
    checkcorrect(3'b010, 3'b010);
    for (int i = 0; i < 5; i = i + 1) begin
      #(BLINKING_LENGTH * CLK_PERIOD); // on => off
      checkcorrect(3'b000, 3'b000);
      #(BLINKING_LENGTH * CLK_PERIOD); // off => on
      checkcorrect(3'b010, 3'b010);
    end
  endtask

  task testoff;
    checkcorrect(3'b000, 3'b000);
    #(BLINKING_LENGTH * CLK_PERIOD / 2);
    checkcorrect(3'b000, 3'b000);
    #(BLINKING_LENGTH * CLK_PERIOD);
    checkcorrect(3'b000, 3'b000);
    #(RED_GREEN_LENGTH * CLK_PERIOD);
    checkcorrect(3'b000, 3'b000);
    #(YELLOW_LENGTH * CLK_PERIOD);
    checkcorrect(3'b000, 3'b000);
  endtask
  //***********************************************************//

endmodule
