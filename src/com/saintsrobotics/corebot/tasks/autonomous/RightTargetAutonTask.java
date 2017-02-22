package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class RightTargetAutonTask extends TurnToFaceVisionTargetTask {
	@Override
    protected void runVisionTask() {
		DriverStation.reportError("Wanted a kick", false);
    	Robot.motors.leftDrive.set(Robot.prefs.getDouble("dead_reckoning_forward_speed", 0));
    	Robot.motors.rightDrive.set(Robot.prefs.getDouble("dead_reckoning_forward_speed", 0));
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_forward_time", 0));
    	Robot.motors.leftDrive.set(-0.05);
    	Robot.motors.rightDrive.set(Robot.prefs.getDouble("dead_reckoning_turn_speed", 0));
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_turn_time", 0));
    	Robot.motors.allDrive.set(0.01);
    	wait.forSeconds(1);
    	seek();
    	moveAcrossField();
    }
	protected void moveAcrossField(){
		Robot.motors.rightDrive.set(-Robot.prefs.getDouble("dead_reckoning_right_turn_speed", 0));
		Robot.motors.leftDrive.set(Robot.prefs.getDouble("dead_reckoning_right_turn_speed", 0));
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_right_turn_time", 0));
    	Robot.motors.allDrive.set(Robot.prefs.getDouble("dead_reckoning_right_side_move_speed", 0));
    	wait.untilWithTimeout(
    		()->{
    			if(Robot.sensors.ultrasound.getDistance() < 
    			Robot.prefs.getDouble("dead_reckoning_safety_distance",40)){
    				DriverStation.reportError("Robot Saw an obstacle at " + Robot.sensors.ultrasound.getDistance(), false);
    				return true;
    			}
    			return false;
    		}, 
    		Robot.prefs.getDouble("dead_reckoning_right_side_move_time", 0));
    	Robot.motors.allDrive.set(-0.01);
	}

}
