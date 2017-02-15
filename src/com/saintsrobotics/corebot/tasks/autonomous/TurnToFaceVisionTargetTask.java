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
        
        int lowestIndex = -1;
        int secondLowestIndex = -1;
        
        double lowestPosition = Integer.MAX_VALUE;
        double secondLowestPosition = Integer.MAX_VALUE;
        
        // if we have more than two data points and the two lists have the same length (should always be true)
        if (centerYArr.length == centerXArr.length && centerYArr.length >= 2) {
            for (int i = 0; i < centerYArr.length; i++) {
                double yPos = centerYArr[i];
                
                // if this y position is less than the lowest on record
                //  * set the second lowest to the (old) lowest
                //  * set the lowest to the current
                if (yPos < lowestPosition) {
                    secondLowestIndex = lowestIndex;
                    lowestIndex = i;
                    
                    secondLowestPosition = lowestPosition;
                    lowestPosition = yPos;
                // else if this y position is less than the second lowest on record
                //  * set the second lowest to the current
                } else if (yPos < secondLowestPosition){
                    secondLowestIndex = i;
                    secondLowestPosition = yPos;
                }
            }
            
            double lowestTargetX = centerXArr[lowestIndex];
            double lowestTargetY = centerYArr[lowestIndex];
            double secondLowestTargetX = centerXArr[secondLowestIndex];
            double secondLowestTargetY = centerYArr[secondLowestIndex];

            double liftPositionX = (lowestTargetX + secondLowestTargetX)/2;
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

            Robot.motors.rightDrive.set(motorPower  * Robot.prefs.getDouble("vision_motor_power", 0));
            Robot.motors.leftDrive.set(motorPower * Robot.prefs.getDouble("vision_motor_power", 0));
            SmartDashboard.putNumber("Vision Target Position", motorPower);
        }
    }
}
