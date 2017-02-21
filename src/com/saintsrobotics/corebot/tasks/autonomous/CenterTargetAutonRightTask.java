package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;

public class CenterTargetAutonRightTask extends RightTargetAutonTask {

	@Override
    protected void runVisionTask() {
    	seek();
    	Robot.motors.leftDrive.set(Robot.prefs.getDouble("dead_reckoning_center_turn_speed", 0));
    	Robot.motors.rightDrive.set(0);
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_turn_time", 0));
    	Robot.motors.allDrive.set(Robot.prefs.getDouble("dead_reckoning_center_move_speed", 0));
    	wait.untilWithTimeout(
    		()->
    			Robot.sensors.ultrasound.getDistance() < 
    			Robot.prefs.getDouble("dead_reckoning_safety_distance",40), 
    		Robot.prefs.getDouble("dead_reckoning_center_move_time", 0));
    	Robot.motors.rightDrive.set(Robot.prefs.getDouble("dead_reckoning_center_right_turn_speed", 0));
    	Robot.motors.leftDrive.set(0);
    	wait.forSeconds(Robot.prefs.getDouble("dead_reckoning_turn_time", 0));
    	moveAcrossField();
    }
}
