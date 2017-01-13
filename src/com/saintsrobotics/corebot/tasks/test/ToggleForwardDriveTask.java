package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.Wait;

public class ToggleForwardDriveTask extends Task {
    
    @Override
    protected void run() {
        Robot.motors.allMotors.set(0.2);
        yield(Wait.forPredicate(() -> Robot.oi.drive.buttons.A()));
        Robot.motors.allMotors.set(0);
    }
}
