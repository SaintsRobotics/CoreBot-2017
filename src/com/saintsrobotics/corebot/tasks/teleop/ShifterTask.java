package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class ShifterTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.buttons.DPAD_LEFT() || Robot.oi.drive.buttons.DPAD_RIGHT());
        if (Robot.oi.drive.buttons.DPAD_LEFT()) { // High gear
            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_OUT - 15);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_OUT + 15);

            wait.forSeconds(0.3);

            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_OUT);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_OUT);
        } else if (Robot.oi.drive.buttons.DPAD_RIGHT()) { // Low gear
            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_IN + 15);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_IN - 15);

            wait.forSeconds(0.3);

            Robot.servos.rightShifter.setAngle(Robot.RIGHT_SHIFTER_IN);
            Robot.servos.leftShifter.setAngle(Robot.LEFT_SHIFTER_IN);
        }
    }
}
