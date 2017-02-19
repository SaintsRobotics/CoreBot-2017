package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import com.saintsrobotics.corebot.util.PID;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToFaceVisionTargetTask extends RunEachFrameTask {
    
    private PID pid = new PID(
            Robot.prefs.getDouble("vision_p", 0),
            Robot.prefs.getDouble("vision_i", 0),
            Robot.prefs.getDouble("vision_d", 0)
    );
    private double motorPower = 0;
    
    @Override
    protected void runEachFrame() {
        
        double[] centerXArr = Robot.visionTable.getNumberArray("centerX", new double[0]);
        double[] centerYArr = Robot.visionTable.getNumberArray("centerY", new double[0]);
        
        int lowestIndex = -1;
        int secondLowestIndex = -1;
        
        double lowestPosition = Integer.MAX_VALUE;
        double secondLowestPosition = Integer.MAX_VALUE;
        double forwardSpeed = Robot.prefs.getDouble("vision_forward_speed",0);
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
                } else if (yPos < secondLowestPosition) {
                    secondLowestIndex = i;
                    secondLowestPosition = yPos;
                }
            }
            
            double lowestTargetX = centerXArr[lowestIndex];
            double lowestTargetY = centerYArr[lowestIndex];
            double secondLowestTargetX = centerXArr[secondLowestIndex];
            double secondLowestTargetY = centerYArr[secondLowestIndex];
            
            double liftPositionX = (lowestTargetX + secondLowestTargetX) / 2;
            double normalizedLiftPositionX = liftPositionX / Robot.cameraWidth;
            double relativeNormalizedLiftPositionX = 2 * (normalizedLiftPositionX - 0.5);
            
            SmartDashboard.putNumber("liftPositionX", liftPositionX);
            SmartDashboard.putNumber("normalizedLiftPositionX", normalizedLiftPositionX);
            SmartDashboard.putNumber("relativeNormalizedLiftPositionX", relativeNormalizedLiftPositionX);
            
            SmartDashboard.putNumber("Vision Target Position", relativeNormalizedLiftPositionX);
            if (relativeNormalizedLiftPositionX > 1 || relativeNormalizedLiftPositionX < -1) {
                DriverStation.reportError("UNEXPECTED VISION TARGET LOCATION: " + relativeNormalizedLiftPositionX, false);
                motorPower = relativeNormalizedLiftPositionX;
            }
            
            motorPower = pid.compute(relativeNormalizedLiftPositionX, 0);
            SmartDashboard.putNumber("PID", motorPower);
            
            //motorPower = Math.signum(relativeNormalizedLiftPositionX) * Math.sqrt(Math.abs(relativeNormalizedLiftPositionX));
            
            double maxMotorPower = Robot.prefs.getDouble("vision_motor_power", 0);
            if (Math.abs(motorPower) > maxMotorPower) {
                motorPower = Math.signum(motorPower) * maxMotorPower;
            }
            
            Robot.motors.rightDrive.set(motorPower+ forwardSpeed);
            Robot.motors.leftDrive.set(-motorPower+ forwardSpeed);
        }else{
        	Robot.motors.rightDrive.set(forwardSpeed);
            Robot.motors.leftDrive.set(forwardSpeed);
        }
    }
}
