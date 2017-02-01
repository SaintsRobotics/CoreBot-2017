package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class GearDropperTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.buttons.RB() || Robot.oi.drive.buttons.LB());
        if (Robot.oi.drive.buttons.RB()) {
            Robot.servos.gearDropArm.setAngle(Robot.GEAR_DROPPER_OUT);
        } else if (Robot.oi.drive.buttons.LB()) {
            Robot.servos.gearDropArm.setAngle(Robot.GEAR_DROPPER_IN);
        }
    }
}
