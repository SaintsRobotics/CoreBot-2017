package com.saintsrobotics.corebot.tasks;


import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.output.Motors;

public class UpdateMotors extends RepeatingTask {

	@Override
	protected void doOnRepeat(double sec) {
		Motors.update(sec);
	}
}
