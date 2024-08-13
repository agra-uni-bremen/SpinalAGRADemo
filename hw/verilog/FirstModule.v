module FirstModule
(
    input clk_i,
    input reset_i,
    input cond_i,
    output wire mySignal,
    output reg myRegister,
    output reg myRegisterWithReset
);

    assign mySignal = cond_i ? 1'b1 : 1'b0;
    
    initial begin
        myRegister = 0;
        myRegisterWithReset = 0;
    end
    
    always @(posedge clk_i)
    begin
        if(cond_i) begin
            myRegister <= myRegister + 1;
        end
    end
    
    always @(posedge clk_i or posedge reset_i)
    begin
        if(cond_i) begin
            myRegisterWithReset <= myRegisterWithReset + 1; 
        end
        if(reset_i) begin
            myRegisterWithReset <= 0;
        end
    end

endmodule
