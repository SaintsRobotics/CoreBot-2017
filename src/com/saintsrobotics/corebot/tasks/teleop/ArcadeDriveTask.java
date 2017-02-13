package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveTask extends RunEachFrameTask {

    private double forwardMultiplier = 1;
    private double turnMultiplier = 1;

    @Override
    protected void runEachFrame() {
        if (Robot.oi.drive.buttons.A()) {
            turnMultiplier = Math.max(0, turnMultiplier - 0.015625);
        } else if (Robot.oi.drive.buttons.B()) {
            turnMultiplier = Math.min(1, turnMultiplier + 0.015625);
        }
        SmartDashboard.putNumber("Turn Multiplier", turnMultiplier);

        if (Robot.oi.drive.buttons.X()) {
            forwardMultiplier = Math.max(0, forwardMultiplier - 0.015625);
        } else if (Robot.oi.drive.buttons.Y()) {
            forwardMultiplier = Math.min(1, forwardMultiplier + 0.015625);
        }
        SmartDashboard.putNumber("Forward Multiplier", forwardMultiplier);

        double forward = -Robot.oi.drive.axes.leftStickY() * forwardMultiplier;
        double turn = Robot.oi.drive.axes.rightStickX() * turnMultiplier;
        
        Robot.motors.leftDrive.set(forward + turn);
        Robot.motors.rightDrive.set(forward - turn);
    }
}
