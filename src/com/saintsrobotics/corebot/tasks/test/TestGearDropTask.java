package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestGearDropTask extends RunEachFrameTask {
    
    @Override
    protected void runEachFrame() {
        double val = Robot.oi.drive.rightTrigger() / 3 - Robot.oi.drive.leftTrigger() / 3;
        SmartDashboard.putNumber("val", val);
        SmartDashboard.putNumber("val2", Robot.oi.drive.rightTrigger());
        Robot.motors.gearDrop.set(val);
        SmartDashboard.putNumber("Potentiometer", Robot.sensors.potentiometer.get());
    }
}
