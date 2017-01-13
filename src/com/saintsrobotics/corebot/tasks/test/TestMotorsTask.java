package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;
import com.saintsrobotics.corebot.coroutine.waiters.WaitForSeconds;

public class TestMotorsTask extends RepeatingTask {
    
    @Override
    protected void doOnRepeat() {
        double speed = 0.2;
        
        Robot.motors.leftDrive1.set(speed);
        yield(new WaitForSeconds(0.2));
        Robot.motors.leftDrive2.set(speed);
        yield(new WaitForSeconds(0.2));
        Robot.motors.leftDrive3.set(speed);
        yield(new WaitForSeconds(0.2));
        Robot.motors.rightDrive1.set(speed);
        yield(new WaitForSeconds(0.2));
        Robot.motors.rightDrive2.set(speed);
        yield(new WaitForSeconds(0.2));
        Robot.motors.rightDrive3.set(speed);
        yield(new WaitForSeconds(0.2));
        Robot.motors.allMotors.set(0);
        yield(new WaitForSeconds(0.2));
    }
}
