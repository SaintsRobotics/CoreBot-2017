package com.saintsrobotics.corebot.tasks.test;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;

public class TestShifterTask extends RepeatingTask {

    @Override
    protected void doOnRepeat() {
        double time = Robot.prefs.getDouble("time", 1);
        double low = Robot.prefs.getDouble("low", -1);
        double high = Robot.prefs.getDouble("high", -1);

        if (low == -1 || high == -1) {
            wait.forFrame();
        }

        Robot.rightShifter.set(low);
        wait.forSeconds(time);

        Robot.rightShifter.set(high);
        wait.forSeconds(time);
    }
}
