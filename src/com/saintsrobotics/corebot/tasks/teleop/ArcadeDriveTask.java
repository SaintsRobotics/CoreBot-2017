package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveTask extends RepeatingTask {

    private double forwardMultiplier = 1;
    private double turnMultiplier = 1;

    @Override
    protected void doOnRepeat() {
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
        
        Robot.motors.leftMotors.set(forward + turn);
        Robot.motors.rightMotors.set(forward - turn);
    }
}
