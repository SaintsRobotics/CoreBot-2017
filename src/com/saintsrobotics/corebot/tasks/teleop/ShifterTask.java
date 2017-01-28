package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class ShifterTask extends RepeatingTask {

    @Override
    protected void doOnRepeat() {
        wait.until(() -> Robot.oi.drive.buttons.RB() || Robot.oi.drive.buttons.LB());
        if (Robot.oi.drive.buttons.RB()) { // High gear
            Robot.rightShifter.set(Robot.RIGHT_SHIFTER_OUT);
            Robot.leftShifter.set(Robot.LEFT_SHIFTER_OUT);
        } else if (Robot.oi.drive.buttons.LB()) { // Low gear
            Robot.rightShifter.set(Robot.RIGHT_SHIFTER_IN);
            Robot.leftShifter.set(Robot.LEFT_SHIFTER_IN);
        }
    }
}
