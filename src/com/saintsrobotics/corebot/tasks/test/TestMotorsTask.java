package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class TestMotorsTask extends RepeatingTask {
    
    @Override
    protected void doOnRepeat() {
        double speed = 0.2;
        
        Robot.motors.leftDrive1.set(speed);
        wait.forSeconds(0.2);
        Robot.motors.leftDrive2.set(speed);
        wait.forSeconds(0.2);
        Robot.motors.rightDrive1.set(speed);
        wait.forSeconds(0.2);
        Robot.motors.rightDrive2.set(speed);
        wait.forSeconds(0.2);
        Robot.motors.lift1.set(speed);
        wait.forSeconds(0.2);
        Robot.motors.lift2.set(speed);
        wait.forSeconds(0.2);
        Robot.motors.allMotors.set(0);
        wait.forSeconds(0.2);
    }
}
