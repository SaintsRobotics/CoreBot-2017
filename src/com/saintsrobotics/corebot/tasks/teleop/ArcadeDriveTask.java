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
        } else if (Robot.oi.drive.B()) {
            turnMultiplier = Math.min(1, turnMultiplier + 0.015625);
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
        
        Robot.motors.leftDrive.set(forward + turn);
        Robot.motors.rightDrive.set(forward - turn);
    }
}
