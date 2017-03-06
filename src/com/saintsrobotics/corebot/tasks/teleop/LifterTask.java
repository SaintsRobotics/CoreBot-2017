package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;

public class LifterTask extends RunEachFrameTask {
    
    private int count = 0;

    @Override
    protected void runEachFrame() {
        double amount = Robot.oi.drive.rightTrigger() - Robot.oi.drive.leftTrigger();

        Robot.motors.lift.set(amount);
        
        if (amount > 0) {
            count++;
        }
        
        if (count > 100) {
            Robot.flags.wantKick = true;
        }
    }
}
