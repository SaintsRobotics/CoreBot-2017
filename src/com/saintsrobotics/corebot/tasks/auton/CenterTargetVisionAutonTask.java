package com.saintsrobotics.corebot.tasks.auton;

public class CenterTargetVisionAutonTask extends BaseAutonTask {
    
    @Override
    protected void runVisionTask() {
        driveIntoLiftAndKickAndBackOff(lineUpSpeedCenter, true);
    }
}
