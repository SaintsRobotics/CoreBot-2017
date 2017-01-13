package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.waiters.WaitForSeconds;

public class DriveStraightAutonTask extends Task {
    
    @Override
    protected void run() {
        Robot.motors.allMotors.set(0.3);
        yield(new WaitForSeconds(3));
        Robot.motors.allMotors.set(0);
    }
}
