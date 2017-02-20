package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;
import com.saintsrobotics.corebot.util.PID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearDropTask extends RunContinuousTask {
    
    private PID armPid = new PID(Robot.prefs.getDouble("P value", 0.1),
        Robot.prefs.getDouble("I value", 0),
        Robot.prefs.getDouble("D value", 0));

    private double gearDropOut = Robot.prefs.getDouble("geardrop_out", -1);
    private double gearDropIn = Robot.prefs.getDouble("geardrop_in", -1);
        
    @Override
    protected void runContinuously() {
        while (!Robot.oi.drive.buttons.RB()) {
            double value = -armPid.compute(Robot.sensors.potentiometer.get(), gearDropIn);
            SmartDashboard.putNumber("geardrop_in_motor", value);
            Robot.motors.gearDrop.set(Math.signum(value)*Math.min(Math.abs(value), 0.2));
            wait.forFrame();
        }
    
        if (gearDropOut != -1 && gearDropIn != -1) {
            
            while (Robot.oi.drive.buttons.RB()) {
                double value = -armPid.compute(Robot.sensors.potentiometer.get(), gearDropOut);
                SmartDashboard.putNumber("geardrop_out_motor", value);
                Robot.motors.gearDrop.set(Math.signum(value) * Math.min(Math.abs(value), 1.0));
                wait.forFrame();
            }
            
            long startTime = System.currentTimeMillis();
    
            while (System.currentTimeMillis() < startTime + 1000) {
                double value = -armPid.compute(Robot.sensors.potentiometer.get(), gearDropIn);
                SmartDashboard.putNumber("geardrop_in_motor", value);
                Robot.motors.gearDrop.set(Math.signum(value) * Math.min(Math.abs(value), 1.0));
                wait.forFrame();
            }
        }
    }
}
