package com.saintsrobotics.corebot.tasks.vision;

public class CenterTargetBasicAutonTask extends BaseAutonTask {
    
    @Override
    protected void runVisionTask() {
        driveIntoLiftAndKickAndBackOff(true);
    }
}
