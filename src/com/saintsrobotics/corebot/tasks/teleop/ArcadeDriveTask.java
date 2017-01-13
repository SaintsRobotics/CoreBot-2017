package com.saintsrobotics.corebot.tasks.teleop;
import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class ArcadeDriveTask extends RepeatingTask {

    @Override
    protected void doOnRepeat() {
        double forward = -Robot.oi.drive.axes.leftStickY();
        double turn = Robot.oi.drive.axes.rightStickX();
        
        Robot.motors.leftMotors.set(forward + turn);
        Robot.motors.rightMotors.set(forward - turn);
    }
}
