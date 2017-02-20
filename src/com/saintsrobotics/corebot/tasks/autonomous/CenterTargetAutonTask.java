package com.saintsrobotics.corebot.tasks.autonomous;

public class CenterTargetAutonTask extends TurnToFaceVisionTargetTask {

	@Override
    protected void runTask() {
    	seek();
    }
}
