package com.saintsrobotics.corebot.tasks.auton;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.DriverStation;

public class CenterTargetDeadReckoningAutonTask extends BaseAutonTask {
    
    @Override
    protected void runVisionTask() {
        driveForward(0.4, 0.5);
        wait.until(() -> {
            if (Robot.sensors.ultrasound.getDistance() < visionStopDistance) {
                DriverStation.reportError("Obstacle spotted, stopping! Distance: " + Robot.sensors.ultrasound.getDistance(), false);
                return true;
            }
            return false;
        });
        driveForward(0.6, 0.8);
        kickGear(0.5);
        stop(0.5);
        
        driveForward(-0.4, 0.8);
        stop(0);
        Robot.flags.wantKick = false;
    }
}
