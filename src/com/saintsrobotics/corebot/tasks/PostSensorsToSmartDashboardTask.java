package com.saintsrobotics.corebot.tasks;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PostSensorsToSmartDashboardTask extends RunEachFrameTask {
    
    @Override
    protected void runEachFrame() {
        Robot.sensors.ultrasound.update();
        SmartDashboard.putNumber("Ultrasound", Robot.sensors.ultrasound.getDistance());
        SmartDashboard.putNumber("Potentiometer", Robot.sensors.potentiometer.get());
    }
}
