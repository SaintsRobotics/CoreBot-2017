package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.DriverStation;

public class CenterTargetAutonRightTask extends TurnToFaceVisionTargetTask {
    
    @Override
    protected void runVisionTask() {
        double deadReckoningCenterTurnSpeed = Robot.prefs.getDouble("dead_reckoning_center_turn_speed", 0);
        double deadReckoningCenterTurnTime = Robot.prefs.getDouble("dead_reckoning_center_turn_time", 0);
        
        double deadReckoningCenterMoveSpeed = Robot.prefs.getDouble("dead_reckoning_center_move_speed", 0);
        double deadReckoningSafetyDistance = Robot.prefs.getDouble("dead_reckoning_safety_distance", 40);
        double deadReckoningCenterMoveTime = Robot.prefs.getDouble("dead_reckoning_center_move_time", 0);
        
        double deadReckoningCenterRightTurnSpeed = Robot.prefs.getDouble("dead_reckoning_center_right_turn_speed", 0);
        double deadReckoningTurnTime = Robot.prefs.getDouble("dead_reckoning_turn_time", 0);
        
        seek();
        
        Robot.motors.leftDrive.set(deadReckoningCenterTurnSpeed);
        Robot.motors.rightDrive.set(-deadReckoningCenterTurnSpeed);
        wait.forSeconds(deadReckoningCenterTurnTime);
        
        Robot.motors.allDrive.set(deadReckoningCenterMoveSpeed);
        wait.untilWithTimeout(
                () -> {
                    if (Robot.sensors.ultrasound.getDistance() < deadReckoningSafetyDistance) {
                        DriverStation.reportError("Obstacle spotted, stopping! Distance: " + Robot.sensors.ultrasound.getDistance(), false);
                        return true;
                    }
                    return false;
                },
                deadReckoningCenterMoveTime);
        
        Robot.motors.rightDrive.set(deadReckoningCenterRightTurnSpeed);
        Robot.motors.leftDrive.set(-deadReckoningCenterRightTurnSpeed);
        wait.forSeconds(deadReckoningTurnTime);
        //Robot.motors.rightDrive.set(Robot.prefs.getDouble("dead_reckoning_center_right_turn_speed", 0));
    }
}
