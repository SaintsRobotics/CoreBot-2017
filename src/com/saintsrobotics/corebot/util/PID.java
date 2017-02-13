package com.saintsrobotics.corebot.util;

public class PID {

    private double kp;
    private double ki;
    private double kd;
    
    private double lastError = 0;
    public double errorSum = 0;
    
    private double outMin = -1.0;
    private double outMax = 1.0;

    public PID(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }
    
    public double compute(double input, double setpoint) {
        double error = setpoint - input;
        
        errorSum += error*ki;
        if (errorSum < outMin) errorSum = outMin; 
        if (errorSum > outMax) errorSum = outMax; 
        
        
        double output = kp*error + errorSum - kd*(error-lastError);
        if (output < outMin) output = outMin; 
        if (output > outMax) output = outMax;
        
        lastError = error;
        return output;
    }
}
