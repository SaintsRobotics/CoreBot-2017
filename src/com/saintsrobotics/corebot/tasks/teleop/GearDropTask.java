package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;
import com.saintsrobotics.corebot.util.PID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearDropTask extends RunContinuousTask {
    
    private PID armPid = new PID(
            0,
            Robot.prefs.getDouble("geardrop_i", 0),
            Robot.prefs.getDouble("geardrop_d", 0));
    
    @Override
    protected void runContinuously() {
        double normalKp = Robot.prefs.getDouble("geardrop_p", 0);
        double holdKp = Robot.prefs.getDouble("geardrop_p_hold", 0);
        
        double gearDropIn = Robot.motors.getGearDropIn();
        double gearDropOut = Robot.motors.getGearDropOut();
        
        armPid.kp = holdKp;
        while (!(Robot.oi.drive.RB() || Robot.flags.wantKick)) {
            armPid.errorSum = 0;
            double value = armPid.compute(Robot.sensors.potentiometer.get(), gearDropIn);
            SmartDashboard.putNumber("geardrop_in_motor", value);
            if (value < 0) value = 0;
            if (value > 0.6) value = 0.6;
            Robot.motors.gearDrop.set(value);
            wait.forFrame();
        }
        armPid.kp = normalKp;
        
        if (gearDropOut != -1 && gearDropIn != -1) {
            
            while (Robot.oi.drive.RB() || Robot.flags.wantKick) {
                double value = armPid.compute(Robot.sensors.potentiometer.get(), gearDropOut);
                SmartDashboard.putNumber("geardrop_out_motor", value);
                Robot.motors.gearDrop.set(Math.signum(value) * Math.min(Math.abs(value), 1.0));
                wait.forFrame();
            }
            
            long outButtonEndTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < outButtonEndTime + 2000) {
                double value = armPid.compute(Robot.sensors.potentiometer.get(), gearDropOut);
                SmartDashboard.putNumber("geardrop_out_motor", value);
                Robot.motors.gearDrop.set(Math.signum(value) * Math.min(Math.abs(value), 1.0));
                wait.forFrame();
            }
            
            long outEndTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < outEndTime + 1000) {
                double value = armPid.compute(Robot.sensors.potentiometer.get(), gearDropIn);
                SmartDashboard.putNumber("geardrop_in_motor", value);
                Robot.motors.gearDrop.set(Math.signum(value) * Math.min(Math.abs(value), 1.0));
                wait.forFrame();
            }
        }
    }
}
