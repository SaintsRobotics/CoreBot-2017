package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class TestMotorsTask extends RunContinuousTask {
    
    @Override
    protected void runContinuously() {
        double speed = 0.2;
        double time = 1;
        
        Robot.motors.leftDrive1.set(speed);
        wait.forSeconds(time);
        Robot.motors.leftDrive2.set(speed);
        wait.forSeconds(time);
        Robot.motors.rightDrive1.set(speed);
        wait.forSeconds(time);
        Robot.motors.rightDrive2.set(speed);
        wait.forSeconds(time);
        Robot.motors.lift1.set(speed);
        wait.forSeconds(time);
        Robot.motors.lift2.set(speed);
        wait.forSeconds(time);
        Robot.motors.allDrive.set(0);
        wait.forSeconds(time*2);
    }
}
