package com.saintsrobotics.corebot.tasks.vision;

public class RightTargetAutonTask extends BaseAutonTask {
    
    @Override
    protected void runVisionTask() {
        driveForward(deadReckoningForwardSpeed, deadReckoningForwardTime);
        turnLeft(deadReckoningTurnSpeed, deadReckoningTurnTime);
        stop(0.4);
        
        driveIntoLiftAndKickAndBackOff(true);
        
//        rotateRight(deadReckoningRightTurnSpeed, deadReckoningRightTurnTime);
//
//        driveForward(deadReckoningRightSideMoveSpeed, 0);
//        waitUntilObstacleSpottedOrTimeout(deadReckoningRightSideMoveTime);
        
        stop(0);
    }
}
