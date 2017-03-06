package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;

public class LifterTask extends RunEachFrameTask {
    
//    boolean currentlyOut = false;
//    boolean lastVal = false;
    int count = 0;

    @Override
    protected void runEachFrame() {
        double amount = Robot.oi.drive.rightTrigger() - Robot.oi.drive.leftTrigger();

        Robot.motors.lift.set(amount);
        
        if (amount != 0) {
            count++;
        }
        
        if (count > 150) {
            Robot.flags.wantKick = true;
        }
    }

//        boolean currentVal = Robot.oi.drive.SELECT();
//        boolean justPressed = currentVal && !lastVal;
//
//        if (justPressed) {
//            currentlyOut = !currentlyOut;
//            if (!currentlyOut) Robot.flags.wantKick = false;
//        }
//
//        if (currentlyOut) {
//            Robot.flags.wantKick = true;
//        }
//
//        lastVal = currentVal;
//    }
}
