package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class ShifterTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.buttons.RB() || Robot.oi.drive.buttons.LB());
        if (Robot.oi.drive.buttons.RB()) { // High gear
            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_OUT - 20);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_OUT + 20);

            wait.forSeconds(0.5);

            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_OUT);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_OUT);
        } else if (Robot.oi.drive.buttons.LB()) { // Low gear
            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_IN + 20);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_IN - 20);

            wait.forSeconds(0.5);

            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_IN);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_IN);
        }
    }
}
