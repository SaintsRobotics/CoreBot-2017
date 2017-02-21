package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;

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
		Robot.motors.allDrive.set(Robot.prefs.getDouble("dead_reckoning_left_side_move_speed", 0));
    	wait.untilWithTimeout(
    		()->
    			Robot.sensors.ultrasound.getDistance() < 
    			Robot.prefs.getDouble("dead_reckoning_safety_distance",40), 
    		Robot.prefs.getDouble("dead_reckoning_left_side_move_time", 0));
		Robot.motors.leftDrive.set(Robot.prefs.getDouble("dead_reckoning_diagonal_turn_speed", 0));
    	Robot.motors.rightDrive.set(0);
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_diagonal_turn_time", 0));
		Robot.motors.allDrive.set(Robot.prefs.getDouble("dead_reckoning_diagonal_move_speed", 0));
    	wait.untilWithTimeout(
    		()->
    			Robot.sensors.ultrasound.getDistance() < 
    			Robot.prefs.getDouble("dead_reckoning_safety_distance",40), 
    		Robot.prefs.getDouble("dead_reckoning_diagonal_move_time", 0));
    	Robot.motors.allDrive.set(0);
	}

}
