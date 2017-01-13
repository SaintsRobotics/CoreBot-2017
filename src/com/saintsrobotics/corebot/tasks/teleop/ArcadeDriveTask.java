package com.saintsrobotics.corebot.tasks.teleop;
import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class ArcadeDriveTask extends RepeatingTask {

	@Override
	protected void doOnRepeat() {
        double forward = -Robot.oi.drive.axis.leftStickY();
        double turn = Robot.oi.drive.axis.rightStickX();
        
        Robot.motors.leftMotors.set(forward + turn);
        Robot.motors.rightMotors.set(forward - turn);
	}
}
