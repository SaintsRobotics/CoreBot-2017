package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class OldRightTargetAutonTask extends TurnToFaceVisionTargetTask {
    
    @Override
    protected void runVisionTask() {
        double deadReckoningForwardSpeed = Robot.prefs.getDouble("dead_reckoning_forward_speed", 0);
        double deadReckoningForwardTime = Robot.prefs.getDouble("dead_reckoning_forward_time", 0);
        double deadReckoningTurnSpeed = Robot.prefs.getDouble("dead_reckoning_turn_speed", 0);
        double deadReckoningTurnTime = Robot.prefs.getDouble("dead_reckoning_turn_time", 0);
        
        // Drive forward
        Robot.motors.allDrive.set(deadReckoningForwardSpeed);
        wait.forSeconds(deadReckoningForwardTime);
        
        // Turn to the right
        Robot.motors.leftDrive.set(-0.05);
        Robot.motors.rightDrive.set(deadReckoningTurnSpeed);
        wait.forSeconds(deadReckoningTurnTime);
        
        // Roll to a stop. Set to 0.01 to avoid coasting
        Robot.motors.allDrive.set(0.01);
        wait.forSeconds(1);
        
        seek();
        moveAcrossField();
    }
    
    protected void moveAcrossField() {
        double deadReckoningRightTurnSpeed = Robot.prefs.getDouble("dead_reckoning_right_turn_speed", 0);
        double deadReckoningRightTurnTime = Robot.prefs.getDouble("dead_reckoning_right_turn_time", 0);
        double deadReckoningRightSideMoveSpeed = Robot.prefs.getDouble("dead_reckoning_right_side_move_speed", 0);
        double deadReckoningSafetyDistance = Robot.prefs.getDouble("dead_reckoning_safety_distance", 40);
        double deadReckoningRightSideMoveTime = Robot.prefs.getDouble("dead_reckoning_right_side_move_time", 0);
                
        Robot.motors.rightDrive.set(-deadReckoningRightTurnSpeed);
        Robot.motors.leftDrive.set(deadReckoningRightTurnSpeed);
        wait.forSeconds(deadReckoningRightTurnTime);
        
        Robot.motors.allDrive.set(deadReckoningRightSideMoveSpeed);
        
        wait.untilWithTimeout(
                () -> {
                    if (Robot.sensors.ultrasound.getDistance() < deadReckoningSafetyDistance) {
                        DriverStation.reportError("Obstacle spotted, stopping! Distance: " + Robot.sensors.ultrasound.getDistance(), false);
                        return true;
                    }
                    return false;
                }, deadReckoningRightSideMoveTime);
        Robot.motors.allDrive.set(-0.01);
    }
}
