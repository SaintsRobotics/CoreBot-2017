package com.saintsrobotics.corebot.tasks.auton;

public class CrossBaselineTask extends BaseAutonTask {
    
    @Override
    protected void runVisionTask() {
        driveForward(0.4, 1.5);
        stop(0);
    }
}
