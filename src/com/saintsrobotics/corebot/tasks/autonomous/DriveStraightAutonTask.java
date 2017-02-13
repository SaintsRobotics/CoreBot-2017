package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;

public class DriveStraightAutonTask extends Task {
    
    @Override
    protected void run() {
        Robot.motors.allDrive.set(0.3);
        wait.forSeconds(3);
        Robot.motors.allDrive.set(0);
    }
}
