package com.saintsrobotics.corebot.tasks;


import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;

public class UpdateMotors extends RunEachFrameTask {

	@Override
	protected void runEachFrame() {
		Robot.motors.update();
	}
}
