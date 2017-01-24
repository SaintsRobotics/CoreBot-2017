package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class LifterTask extends RepeatingTask {

    @Override
    protected void doOnRepeat() {
        double amount = Robot.oi.drive.axes.rightTrigger() - Robot.oi.drive.axes.leftTrigger();

        Robot.motors.liftMotors.set(amount);
    }
}
