package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToFaceVisionTargetTask extends RunEachFrameTask {

    @Override
    protected void runEachFrame() {
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
            double relativeNormalizedLiftPositionX = normalizedLiftPositionX - 0.5;

            double motorPower = 2 * relativeNormalizedLiftPositionX;
            if (motorPower > 1) motorPower = 1;
            if (motorPower < -1) motorPower = -1;
            motorPower = Math.sqrt(motorPower);

            SmartDashboard.putNumber("Vision Target Position", motorPower);

            Robot.motors.rightMotors.set(motorPower);
            Robot.motors.leftMotors.set(motorPower);
        } else {
            Robot.motors.rightMotors.set(0);
            Robot.motors.leftMotors.set(0);
        }
    }
}
