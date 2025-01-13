`timescale 1ns/1ns

module alu_tb;

  //**************** ADDITIONAL TESTING VALUES ****************//
  logic         ERROR;              // Will be 1 if any test failed
  logic         SUB_ERROR;          // Will be 1 if any test of the current subtask failed
  //***********************************************************//

  //********************* MODULE INPUTS ***********************//
  logic [31:0]  A, B;
  logic [1:0]   OPC;
  //***********************************************************//

  //********************* MODULE OUTPUTS **********************//
  logic [31:0]  R;
  //***********************************************************//

  //******************* UUT INSTANTIATION *********************//
  alu uut(
        .A(A),
        .B(B),
        .OPC(OPC),
        .R(R));
  //***********************************************************//

  //********************* TEST INITIATION *********************//
  initial begin
    $dumpfile("alu_tb.vcd");
    $timeformat(-9, 0, " ns", 8);
    $dumpvars;

    // Initialize Additional Testing Values
    ERROR       = 1'b0;
    SUB_ERROR   = 1'b0;

    $display("************* STARTING SIMULATION *************");

    $display("### Testing addition (OPC=00) ###");
    OPC = 2'b00;
    A = 1;
    B = 1;
    #1
    if (R === 0) begin
      $display("  Addition is not implemented");
      ERROR = 1'b1;
    end else begin
      A = 3;
      B = 5;
      #1
      if (R !== A + B) begin
        $display("  ERROR: %d+%d, expected result %d but got %d", A, B, A + B, R);
        SUB_ERROR = 1'b1;
      end
      A = 399;
      B = 21;
      #1
      if (R !== A + B) begin
        $display("  ERROR: %d+%d, expected result %d but got %d", A, B, A + B, R);
        SUB_ERROR = 1'b1;
      end

      if (SUB_ERROR == 0) begin
        $display("  Success");
      end
    end
    
    ERROR = ERROR | SUB_ERROR;
    SUB_ERROR   = 1'b0;

    $display("### Testing arithmetic right shift (OPC=01) ###");
    OPC = 2'b01;
    A = 32'b111111111111;
    B = 1;
    #1
    if (R === 0) begin
      $display("  Arithmetic right shift is not implemented");
      ERROR = 1'b1;
    end else begin
      A = 0;
      B = 5;
      #1
      if (R !== A <<< B) begin
        $display("  ERROR: %b<<<%d, expected result %b but got %b", A, B, A <<< B, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'b1111;
      B = 2;
      #1
      if (R !== A <<< B) begin
        $display("  ERROR: %b<<<%d, expected result %b but got %b", A, B, A <<< B, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'b1111;
      B = 0;
      #1
      if (R !== A <<< B) begin
        $display("  ERROR: %b<<<%d, expected result %b but got %b", A, B, A <<< B, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'b1111;
      B = 3;
      #1
      if (R !== A <<< B) begin
        $display("  ERROR: %b<<<%d, expected result %b but got %b", A, B, A <<< B, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'hFFFFFFFF;
      B = 20;
      #1
      if (R !== A <<< B) begin
        $display("  ERROR: %b<<<%d, expected result %b but got %b", A, B, A <<< B, R);
        SUB_ERROR = 1'b1;
      end

      if (SUB_ERROR == 0) begin
        $display("  Success");
      end
    end
    
    ERROR = ERROR | SUB_ERROR;
    SUB_ERROR   = 1'b0;

    $display("### Testing parity check (OPC=10) ###");
    OPC = 2'b10;
    A = 32'b1;
    B = 1;
    #1
    if (R === 0) begin
      $display("  Arithmetic right shift is not implemented");
      ERROR = 1'b1;
    end else begin
      A = 32'h00000000;
      B = 5;
      #1
      if (R !== ^A) begin
        $display("  ERROR: parity(%b), expected result %b but got %b", A, ^A, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'hFFFFFFFF;
      B = 5;
      #1
      if (R !== ^A) begin
        $display("  ERROR: parity(%b), expected result %b but got %b", A, ^A, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'hFFFEFFFF;
      B = 5;
      #1
      if (R !== ^A) begin
        $display("  ERROR: parity(%b), expected result %b but got %b", A, ^A, R);
        SUB_ERROR = 1'b1;
      end
      A = 32'h00FF01FF;
      B = 5;
      #1
      if (R !== ^A) begin
        $display("  ERROR: parity(%b), expected result %b but got %b", A, ^A, R);
        SUB_ERROR = 1'b1;
      end

      if (SUB_ERROR == 0) begin
        $display("  Success");
      end
    end
    
    ERROR = ERROR | SUB_ERROR;
    SUB_ERROR   = 1'b0;

    $display("### Testing undefined operation (OPC=11) ###");
    OPC = 2'b11;
    A = 32'd4_8_15;
    B = 32'd16_23_42;
    #1
    if (R === 0) begin
      $display("  Success");
    end else begin
      SUB_ERROR = 1'b1;
      $display("  ERROR: For undefined operation, expected result %b but got %b", 0, R);
    end
    
    ERROR = ERROR | SUB_ERROR;
    SUB_ERROR   = 1'b0;

    $display("************* SIMULATION COMPLETE *************");

    if (ERROR == 0) begin
      $display("All tests succeeded");
    end else begin
      $display("THERE WERE ERRORS (or not implemented operations)");
    end

    $finish;
  end
  //***********************************************************//
	
endmodule
