`timescale 1ns / 1ns

module advanced_traffic_light

    ( input   logic   CLK,
      input   logic   RESET,      // Only for traffic_light module
      input   logic   NIGHT,      // If set, just show blinking yellow light
      input   logic   OFF,        // If set, disable all lights, also in night mode

      output  logic   X_RED,      // Red light on road X
      output  logic   Y_RED,      // Red light on road Y
      output  logic   X_YELLOW,   // Yellow light on road X
      output  logic   Y_YELLOW,   // Yellow light on road Y
      output  logic   X_GREEN,    // Green light on road X
      output  logic   Y_GREEN);   // Green light on road Y

  localparam BLINKING_LENGTH = 50;  // CLK cycles for each yellow being on or off during blinking
                                    // in night mode

/* ====================================== INSERT CODE HERE ====================================== */



/* ============================================================================================== */

endmodule
