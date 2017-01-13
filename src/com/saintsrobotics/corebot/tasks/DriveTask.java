package com.saintsrobotics.corebot.tasks;
import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.waiters.WaitForFrame;

public class DriveTask extends Task {

	@Override
	protected void run() {
		while(true) {
			double forward = -Robot.oi.drive.axis.leftStickY();
			double turn = Robot.oi.drive.axis.rightStickX();
			Robot.motors.leftDrive1.set(forward + turn);
			Robot.motors.leftDrive2.set(forward + turn);
			Robot.motors.leftDrive3.set(forward + turn);
			Robot.motors.rightDrive1.set(forward - turn);
			Robot.motors.rightDrive2.set(forward - turn);
			Robot.motors.rightDrive3.set(forward - turn);
			yield(new WaitForFrame());
		}
	}
}
