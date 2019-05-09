package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class BackUpATadTask extends RunContinuousTask {
    
    private final double backUpSpeed = Robot.prefs.getDouble("back_up_speed", 0);
    private final double backUpTime = Robot.prefs.getDouble("back_up_time", 0);
    
    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.LB());
        
        Robot.flags.disableDrive = true;
        
        Robot.motors.allDrive.set(backUpSpeed);
        wait.forSeconds(backUpTime);
        Robot.motors.allDrive.set(0.01);
        
        Robot.motors.ledController.turnOnLED();
        wait.forSeconds(0.3);
        Robot.motors.ledController.turnOffLED();
        
        Robot.flags.disableDrive = false;
    }
}
