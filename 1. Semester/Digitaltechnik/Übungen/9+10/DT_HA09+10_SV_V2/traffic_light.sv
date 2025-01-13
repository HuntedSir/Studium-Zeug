`timescale 1ns / 1ns

module traffic_light

    ( input  logic  CLK,
      input  logic  RESET,      // After RESET, road X should immediately start green phase and road
                                // Y should start red phase

      output logic  X_RED,      // Red light on road X
      output logic  Y_RED,      // Red light on road Y
      output logic  X_YELLOW,   // Yellow light on road X
      output logic  Y_YELLOW,   // Yellow light on road Y
      output logic  X_GREEN,    // Green light on road X
      output logic  Y_GREEN);   // Green light on road Y

  logic [11:0] counter, next_counter;

  logic [1:0] state, next_state; // 0: green for X, 1: yellow for X, red-yellow for Y
                                 // 2: green for Y, 3: yellow for Y, red-yellow for X

  // Register for counter
  always_ff @(posedge CLK) begin
    if (RESET) begin
      counter <= 0;
    end else begin
      counter <= next_counter;
    end
  end

  // Combinatorial logic for counter, increase counter or reset if maximum for current state reached
  always_comb case (state)
    2'd0:   next_counter = (counter == 4000) ? 1 : counter + 1;
    2'd1:   next_counter = (counter ==  500) ? 1 : counter + 1;
    2'd2:   next_counter = (counter == 4000) ? 1 : counter + 1;
    2'd3:   next_counter = (counter ==  500) ? 1 : counter + 1;
  endcase

/* ====================================== INSERT CODE HERE ====================================== */

  // Register for state

  always_ff @( posedge CLK ) begin
  if (RST) begin
    state <= 2'd0; 
  end else begin
    state <= nextstate; 
  end
  end

  // Next state logic

  
  always_comb case (state)

    2'd0: next_state = next_counter ? 2'd1 : 2'd0;
    2'd1: next_state = next_counter ? 2'd2 : 2'd1;
    2'd2: next_state = next_counter ? 2'd3 : 2'd2;
    2'd3: next_state = next_counter ? 2'd0 : 2'd3;
    default: next_state = 2'd0;

  endcase

  // Output logic

    assign X_RED = (state == 2'd2) | (state == 2'd3);
    assign Y_RED = (state == 2'd0) | (state == 2'd1); 
    assign X_YELLOW = (state == 2'd1) | (state == 2'd3);
    assign Y_YELLOW = (state == 2'd1) | (state == 2'd3);
    assign X_GREEN = (state == 2'd0);
    assign Y_GREEN = (state == 2'd2);

/* ============================================================================================== */

endmodule
