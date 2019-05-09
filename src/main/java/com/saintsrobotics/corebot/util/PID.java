package com.saintsrobotics.corebot.util;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID {
    
    private final double maxSum;

    public double kp;
    private double ki;
    private double kd;
    
    private double lastError = 0;
    public double errorSum = 0;
    
    private double outMin = -1;
    private double outMax = 1;

    public PID(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        maxSum = Robot.prefs.getDouble("geardrop_maxsum", 0);
    }
    
    public double compute(double input, double setpoint) {
        double error = setpoint - input;
        
        errorSum += error*ki;
        if (errorSum < -maxSum) errorSum = -maxSum;
        if (errorSum > maxSum) errorSum = maxSum;
    
        SmartDashboard.putNumber("pid_error_sum", errorSum);
        
        double output = kp*error + errorSum - kd*(error-lastError);
        if (output < outMin) output = outMin; 
        if (output > outMax) output = outMax;
        
        lastError = error;
        return output;
    }
}
