package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToFaceVisionTargetTask extends RunEachFrameTask {

    @Override
    protected void runEachFrame() {
        double motorPower = 0;

        double[] centerXArr = Robot.visionTable.getNumberArray("centerX", new double[0]);
        double[] centerYArr = Robot.visionTable.getNumberArray("centerY", new double[0]);
        if (centerXArr.length == 2 && centerYArr.length == 2) {
            // no guarantee these are actually on the right and left
            double leftTargetX = centerXArr[0];
            double leftTargetY = centerYArr[0];
            double rightTargetX = centerXArr[1];
            double rightTargetY = centerYArr[1];

            double liftPositionX = (leftTargetX + rightTargetX)/2;
            double normalizedLiftPositionX = liftPositionX/Robot.cameraWidth;
            double relativeNormalizedLiftPositionX = 2 * (normalizedLiftPositionX - 0.5);

            SmartDashboard.putNumber("liftPositionX", liftPositionX);
            SmartDashboard.putNumber("normalizedLiftPositionX", normalizedLiftPositionX);
            SmartDashboard.putNumber("relativeNormalizedLiftPositionX", relativeNormalizedLiftPositionX);

            motorPower = Math.signum(relativeNormalizedLiftPositionX) * Math.sqrt(Math.abs(relativeNormalizedLiftPositionX));

            if (motorPower > 1 || motorPower < -1) {
                DriverStation.reportError("UNEXPECTED VISION TARGET LOCATION: " + motorPower, false);
                motorPower = 0;
            }

        }
        Robot.motors.rightDrive.set(motorPower  * Robot.prefs.getDouble("vision_motor_power", 0));
        Robot.motors.leftDrive.set(motorPower * Robot.prefs.getDouble("vision_motor_power", 0));
        SmartDashboard.putNumber("Vision Target Position", motorPower);
    }
}
