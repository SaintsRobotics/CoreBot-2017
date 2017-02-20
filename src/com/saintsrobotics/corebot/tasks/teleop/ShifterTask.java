package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class ShifterTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.buttons.L3() || Robot.oi.drive.buttons.R3());
        if (Robot.oi.drive.buttons.R3()) { // High gear
            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_OUT - 15);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_OUT + 15);

            wait.forSeconds(0.6);

            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_OUT);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_OUT);
        } else if (Robot.oi.drive.buttons.L3()) { // Low gear
            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_IN + 15);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_IN - 15);

            wait.forSeconds(0.6);

            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_IN);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_IN);
        }
    }
}
