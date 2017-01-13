package com.saintsrobotics.corebot.tasks.teleop;
import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class ArcadeDriveTask extends RepeatingTask {

	@Override
	protected void doEveryTick() {
        double forward = -Robot.oi.drive.axis.leftStickY();
        double turn = Robot.oi.drive.axis.rightStickX();
        
        Robot.motors.leftDrive1.set(forward + turn);
        Robot.motors.leftDrive2.set(forward + turn);
        Robot.motors.leftDrive3.set(forward + turn);
        Robot.motors.rightDrive1.set(forward - turn);
        Robot.motors.rightDrive2.set(forward - turn);
        Robot.motors.rightDrive3.set(forward - turn);
	}
}
