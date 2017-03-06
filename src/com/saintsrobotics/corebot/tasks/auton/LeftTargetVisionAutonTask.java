package com.saintsrobotics.corebot.tasks.auton;

public class LeftTargetVisionAutonTask extends BaseAutonTask {
    
    @Override
    protected void runVisionTask() {
        driveForward(deadReckoningForwardSpeed, deadReckoningForwardTime);
        turnLeft(-deadReckoningTurnSpeed, deadReckoningTurnTime);
        stop(0.4);
        
        driveIntoLiftAndKickAndBackOff(lineUpSpeedSides, false);

//        rotateRight(deadReckoningRightTurnSpeed, deadReckoningRightTurnTime);
//
//        driveForward(deadReckoningRightSideMoveSpeed, 0);
//        waitUntilObstacleSpottedOrTimeout(deadReckoningRightSideMoveTime);
        
        stop(0);
    }
}
