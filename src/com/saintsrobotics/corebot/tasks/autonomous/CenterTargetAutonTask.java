package com.saintsrobotics.corebot.tasks.autonomous;

import com.saintsrobotics.corebot.Robot;

public class CenterTargetAutonTask extends TurnToFaceVisionTargetTask {

	@Override
    protected void runTask() {
    	seek();
    }
}
