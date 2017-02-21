package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToFaceVisionTargetTask extends Task{
    
    protected void seek(){
    	while(Robot.sensors.ultrasound.getDistance() > Robot.prefs.getDouble("vision_stop_distance",10)){
        	double motorPower = 0;
        	SmartDashboard.putNumber("Ultrasound", Robot.sensors.ultrasound.getDistance());
        	double[] centerXArr = Robot.visionTable.getNumberArray("centerX", new double[0]);
        	double[] centerYArr = Robot.visionTable.getNumberArray("centerY", new double[0]);
        	int lowestIndex = -1;
        	int secondLowestIndex = -1;
        
        	double lowestPosition = Integer.MIN_VALUE;
	        double secondLowestPosition = Integer.MIN_VALUE;
	      //Drew wrote this
            double forwardAmount;
            if(Robot.sensors.ultrasound.getDistance() < Robot.prefs.getDouble("vision_slow_distance",40)){
            	double min = Robot.prefs.getDouble("vision_forward_speed_min",0); 
            	double max = Robot.prefs.getDouble("vision_forward_speed_max",0);
            	double difference = max - min; 
            	forwardAmount = min + difference * Robot.sensors.ultrasound.getDistance() / Robot.prefs.getDouble("vision_slow_distance",40);
            }else{
            	forwardAmount = Robot.prefs.getDouble("vision_coast_speed", 0);
            }
	        	        // if we have more than two data points and the two lists have the same length (should always be true)
	        if (centerYArr.length == centerXArr.length && centerYArr.length >= 2) {
	            for (int i = 0; i < centerYArr.length; i++) {
	                double yPos = centerYArr[i];
	                
	                
	                // if this y position is less than the lowest on record
	                //  * set the second lowest to the (old) lowest
	                //  * set the lowest to the current
	                if (yPos > lowestPosition) {
	                    secondLowestIndex = lowestIndex;
	                    lowestIndex = i;
	                    
	                    secondLowestPosition = lowestPosition;
	                    lowestPosition = yPos;
	                    // else if this y position is less than the second lowest on record
	                    //  * set the second lowest to the current
	                } else if (yPos > secondLowestPosition) {
	                    secondLowestIndex = i;
	                    secondLowestPosition = yPos;
	                }
	            }
	            double lowestTargetX = centerXArr[lowestIndex];
	            double lowestTargetY = centerYArr[lowestIndex];
	            double secondLowestTargetX = centerXArr[secondLowestIndex];
	            double secondLowestTargetY = centerYArr[secondLowestIndex];
	            SmartDashboard.putNumber("targetY",secondLowestTargetY);
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
	            
	            SmartDashboard.putNumber("turnPower", motorPower);
	            if(Math.abs(relativeNormalizedLiftPositionX) > Robot.prefs.getDouble("vision_tolerance",0)){
	            	motorPower = Math.signum(relativeNormalizedLiftPositionX) * Robot.prefs.getDouble("vision_motor_power", 0);
	            }else{
	            	motorPower = 0;
	            }
	            SmartDashboard.putNumber("forward speed", forwardAmount);
	            Robot.motors.rightDrive.set(-motorPower+ forwardAmount);
	            Robot.motors.leftDrive.set(motorPower+ forwardAmount);
	        }else{
	        	Robot.motors.rightDrive.set(forwardAmount);
	            Robot.motors.leftDrive.set(forwardAmount);
	        }
	        wait.forFrame();
        }
        Robot.motors.rightDrive.set(Robot.prefs.getDouble("vision_coast_speed",0));
        Robot.motors.leftDrive.set(Robot.prefs.getDouble("vision_coast_speed",0));
        wait.forSeconds(Robot.prefs.getDouble("vision_coast_time", 0));
        Robot.motors.leftDrive.set(0);
        Robot.motors.rightDrive.set(0);
        //Testing bs below
        wait.forSeconds(Robot.prefs.getDouble("vision_idle_time",0));
        Robot.motors.rightDrive.set(-Robot.prefs.getDouble("vision_coast_speed",0));
        Robot.motors.leftDrive.set(-Robot.prefs.getDouble("vision_coast_speed",0));
        wait.forSeconds(Robot.prefs.getDouble("vision_coast_time", 0));
        Robot.motors.leftDrive.set(0);
        Robot.motors.rightDrive.set(0);
    }
    @Override
    protected void runTask(){
    }
	protected void runVisionTask() {
		seek();
	}
}
