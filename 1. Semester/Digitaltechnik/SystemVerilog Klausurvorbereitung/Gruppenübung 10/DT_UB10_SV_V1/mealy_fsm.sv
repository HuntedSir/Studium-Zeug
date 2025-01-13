`timescale 1ns / 1ns

module mealy_fsm

    ( input   logic   CLK,
      input   logic   RESET,
      input   logic   E,    // FSM input

      output  logic   Z);   // FSM output

/* ====================================== INSERT CODE HERE ====================================== */

  // Register for state
  logic [2:0] state, nextstate;
  always_ff @( posedge CLK ) begin 
    if(RESET) begin
      state <= 0;
    end else begin
      state <= nextstate;
    end
  end

  // Next state logic

  always_ff @( posedge CLK ) begin
    case(state) begin
      0: nextstate = E ? 1 : 0;
      1: nextstate = E ? 2 : 4;
      2: nextstate = E ? 3 : 0;
      3: nextstate = E ? 1 : 4;
      4: nextstate = E ? 3 : 4;
      default: nextstate = 0;
    end
  end

  // Output logic

  assign Z = (state == 2 || 4) && ~E | (state == 3 && E)

/* ============================================================================================== */

endmodule
