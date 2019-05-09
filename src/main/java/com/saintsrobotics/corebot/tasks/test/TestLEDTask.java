package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class TestLEDTask extends RunContinuousTask {
    
    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.START());
        Robot.motors.ledController.turnOnLED();
        wait.until(() -> !Robot.oi.drive.START());
        
        wait.until(() -> Robot.oi.drive.START());
        Robot.motors.ledController.turnOffLED();
        wait.until(() -> !Robot.oi.drive.START());
    }
}
