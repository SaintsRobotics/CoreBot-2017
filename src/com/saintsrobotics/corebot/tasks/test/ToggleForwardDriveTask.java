package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;

public class ToggleForwardDriveTask extends Task {
    
    @Override
    protected void run() {
        Robot.motors.allDriveMotors.set(0.1);
        wait.until(() -> Robot.oi.drive.buttons.A());
        Robot.motors.allDriveMotors.set(0);
    }
}
