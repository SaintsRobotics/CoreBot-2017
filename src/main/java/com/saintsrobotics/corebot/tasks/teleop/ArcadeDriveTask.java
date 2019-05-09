package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveTask extends RunEachFrameTask {

    private double forwardMultiplier = 1;
    private double turnMultiplier = 0.8;

    @Override
    protected void runEachFrame() {
        if (Robot.oi.drive.A()) {
            turnMultiplier = Math.max(0, turnMultiplier - 0.015625);
        } 
        SmartDashboard.putNumber("Turn Multiplier", turnMultiplier);

        if (Robot.oi.drive.X()) {
            forwardMultiplier = Math.max(0, forwardMultiplier - 0.015625);
        } else if (Robot.oi.drive.Y()) {
            forwardMultiplier = Math.min(1, forwardMultiplier + 0.015625);
        }
        SmartDashboard.putNumber("Forward Multiplier", forwardMultiplier);

        
        double forward = -Robot.oi.drive.leftStickY() * forwardMultiplier;
        double turn = Robot.oi.drive.rightStickX() * turnMultiplier;
        
        if (Robot.oi.drive.B()) {
            forward = (-Robot.oi.babyDrive.leftStickY() * forwardMultiplier) * 0.6;
            turn = (Robot.oi.babyDrive.rightStickX() * turnMultiplier) * 0.6;
        }

        SmartDashboard.putNumber("Input Forward", forward);
        SmartDashboard.putNumber("Input Turn", turn);
        
        double minForward = Robot.prefs.getDouble("drive_scaling_forward_min", 0);
        double maxForward = Robot.prefs.getDouble("drive_scaling_forward_max", 1);
        double minTurn = Robot.prefs.getDouble("drive_scaling_turn_min", 0);
        double maxTurn = Robot.prefs.getDouble("drive_scaling_turn_max", 1);
        
        double scalingForward = 1-Math.abs(forward);
        if (scalingForward < minForward) scalingForward = minForward;
        if (scalingForward > maxForward) scalingForward = maxForward;
        
        double scalingRatio = (scalingForward - minForward) / (maxForward - minForward);
        double turnScaling = minTurn + scalingRatio * (maxTurn - minTurn);
        
        turn *= turnScaling;
        
        SmartDashboard.putNumber("Turn Scaling", turnScaling);
        SmartDashboard.putNumber("Left Drive", forward + turn);
        SmartDashboard.putNumber("Right Drive", forward - turn);
        
        if (!Robot.flags.disableDrive) {
            Robot.motors.leftDrive.set(forward + turn);
            Robot.motors.rightDrive.set(forward - turn);
        }
    }
}
