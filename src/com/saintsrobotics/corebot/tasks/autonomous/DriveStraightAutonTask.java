package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.output.MotorGroup;

public class DriveStraightAutonTask extends Task {
    
    @Override
    protected void run() {
    	MotorGroup all = new MotorGroup("left1","left2","left3", "right1","right2","right3");
        all.set(0.3);
        wait.forSeconds(3);
        all.set(0);
    }
}
