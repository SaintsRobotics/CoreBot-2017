package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class TestShifterTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        double time = Robot.prefs.getDouble("test_shifter_time", 1);
        double low = Robot.prefs.getDouble("test_shifter_low", -1);
        double high = Robot.prefs.getDouble("test_shifter_high", -1);

        if (low == -1 || high == -1) {
            wait.forFrame();
        }

        Robot.servos.rightShifter.setAngle(low);
        wait.forSeconds(time);

        Robot.servos.rightShifter.setAngle(high);
        wait.forSeconds(time);
    }
}
