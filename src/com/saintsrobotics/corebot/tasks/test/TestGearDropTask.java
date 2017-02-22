package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;

public class TestGearDropTask extends RunEachFrameTask {
    
    @Override
    protected void runEachFrame() {
        double val = Robot.oi.drive.rightTrigger() / 3 - Robot.oi.drive.leftTrigger() / 3;
        Robot.motors.gearDrop.set(val);
    }
}
