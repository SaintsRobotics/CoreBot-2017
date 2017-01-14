package com.saintsrobotics.corebot.tasks;


import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;
import com.saintsrobotics.corebot.coroutine.Task;

public class UpdateMotors extends RepeatingTask {

	@Override
	protected void doOnRepeat() {
		Robot.motors.update();
	}
}
