package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;
import edu.wpi.first.wpilibj.DriverStation;

public class ArcadeDriveTask extends RepeatingTask {

    private double forwardMultiplier = 1;
    private double turnMultiplier = 1;

    @Override
    protected void doOnRepeat() {
        if (Robot.oi.drive.buttons.A()) {
            turnMultiplier -= 0.01;
            DriverStation.reportWarning("Turn Multiplier: " + turnMultiplier, false);
        } else if (Robot.oi.drive.buttons.B()) {
            turnMultiplier += 0.01;
            DriverStation.reportWarning("Turn Multiplier: " + turnMultiplier, false);
        }

        if (Robot.oi.drive.buttons.X()) {
            forwardMultiplier -= 0.01;
            DriverStation.reportWarning("Forward Multiplier: " + forwardMultiplier, false);
        } else if (Robot.oi.drive.buttons.Y()) {
            forwardMultiplier += 0.01;
            DriverStation.reportWarning("Forward Multiplier: " + forwardMultiplier, false);
        }

        double forward = -Robot.oi.drive.axes.leftStickY() * forwardMultiplier;
        double turn = Robot.oi.drive.axes.rightStickX() * turnMultiplier;
        
        Robot.motors.leftMotors.set(forward + turn);
        Robot.motors.rightMotors.set(forward - turn);
    }
}
