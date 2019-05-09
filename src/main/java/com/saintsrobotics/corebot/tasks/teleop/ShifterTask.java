package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class ShifterTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        wait.until(() -> Robot.oi.drive.L3() || Robot.oi.drive.R3());
        if (Robot.oi.drive.R3()) { // High gear
            Robot.servos.rightShifter.setAngle(Robot.servos.getRightShifterOut() - 15);
            Robot.servos.leftShifter.setAngle(Robot.servos.getLeftShifterOut() + 15);

            wait.forSeconds(0.6);

            Robot.servos.rightShifter.setAngle(Robot.servos.getRightShifterOut());
            Robot.servos.leftShifter.setAngle(Robot.servos.getLeftShifterOut());
        } else if (Robot.oi.drive.L3()) { // Low gear
            Robot.servos.rightShifter.setAngle(Robot.servos.getRightShifterIn() + 15);
            Robot.servos.leftShifter.setAngle(Robot.servos.getLeftShifterIn() - 15);

            wait.forSeconds(0.6);

            Robot.servos.rightShifter.setAngle(Robot.servos.getRightShifterIn());
            Robot.servos.leftShifter.setAngle(Robot.servos.getLeftShifterIn());
        }
    }
}
