package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;
import com.saintsrobotics.corebot.util.PID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearDropTask extends RunContinuousTask {
    
    private PID armPid = new PID(Robot.prefs.getDouble("geardrop_p", 0), 0, 0);

    @Override
    protected void runContinuously() {
        double gearDropIn = Robot.motors.getGearDropIn();
        double gearDropOut = Robot.motors.getGearDropOut();
        
        while (!Robot.oi.drive.RB()) {
            double value = -armPid.compute(Robot.sensors.potentiometer.get(), gearDropIn);
            SmartDashboard.putNumber("geardrop_in_motor", value);
            Robot.motors.gearDrop.set(Math.signum(value)*Math.min(Math.abs(value), 0.2));
            wait.forFrame();
        }
    
        if (gearDropOut != -1 && gearDropIn != -1) {
            
            while (Robot.oi.drive.RB()) {
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
