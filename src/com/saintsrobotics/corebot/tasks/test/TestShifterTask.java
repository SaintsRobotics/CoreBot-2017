package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;

public class TestShifterTask extends RunContinuousTask {

    @Override
    protected void runContinuously() {
        double time = Robot.prefs.getDouble("time", 1);
        double low = Robot.prefs.getDouble("low", -1);
        double high = Robot.prefs.getDouble("high", -1);

        if (low == -1 || high == -1) {
            wait.forFrame();
        }

        Robot.servos.leftShifter.setAngle(low);
        wait.forSeconds(time);

        Robot.servos.leftShifter.setAngle(high);
        wait.forSeconds(time);
    }
}
