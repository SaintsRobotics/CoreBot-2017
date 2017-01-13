package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.waiters.WaitForSeconds;

public class DriveStraightAutonTask extends Task {
    
    @Override
    protected void run() {
        Robot.motors.leftDrive1.set(0.3);
        Robot.motors.leftDrive2.set(0.3);
        Robot.motors.leftDrive3.set(0.3);
        Robot.motors.rightDrive1.set(0.3);
        Robot.motors.rightDrive2.set(0.3);
        Robot.motors.rightDrive3.set(0.3);
        
        yield(new WaitForSeconds(5));
    
        Robot.motors.leftDrive1.set(0);
        Robot.motors.leftDrive2.set(0);
        Robot.motors.leftDrive3.set(0);
        Robot.motors.rightDrive1.set(0);
        Robot.motors.rightDrive2.set(0);
        Robot.motors.rightDrive3.set(0);
    }
}
