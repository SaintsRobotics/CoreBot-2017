package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;

public class LifterTask extends RunEachFrameTask {
    
    private int count = 0;

    @Override
    protected void runEachFrame() {
        double amount = Robot.oi.drive.rightTrigger() - Robot.oi.drive.leftTrigger();
        double babyAmount = Robot.oi.babyDrive.rightTrigger() - Robot.oi.babyDrive.leftTrigger();

        if (Math.abs(babyAmount) > 0.2 && Robot.oi.drive.B()) {
            Robot.motors.lift.set(babyAmount);
        }
        else {
        Robot.motors.lift.set(amount);
        }
        
        if (amount > 0) {
            count++;
        }
        
        if (count > 100) {
            Robot.flags.wantKick = true;
        }
    }
}
