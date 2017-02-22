package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftTargetAutonTask extends TurnToFaceVisionTargetTask {

	@Override
    protected void runVisionTask() {
    	Robot.motors.allDrive.set(Robot.prefs.getDouble("dead_reckoning_forward_speed", 0));
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_forward_time", 0));
    	Robot.motors.rightDrive.set(-0.05);
    	Robot.motors.leftDrive.set(Robot.prefs.getDouble("dead_reckoning_turn_speed", 0));
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_turn_time", 0));
    	Robot.motors.allDrive.set(0);
    	wait.forSeconds(1);
    	seek();
    	Robot.motors.leftDrive.set(-Robot.prefs.getDouble("dead_reckoning_turn_speed", 0));
    	Robot.motors.rightDrive.set(0);
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_turn_time", 0));
    	turnDiagonalAndMove();
    }
	protected void turnDiagonalAndMove(){
		Robot.motors.leftDrive.set(-Robot.prefs.getDouble("dead_reckoning_right_turn_speed", 0));
		Robot.motors.rightDrive.set(Robot.prefs.getDouble("dead_reckoning_right_turn_speed", 0));
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
