package com.saintsrobotics.corebot.tasks;


import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.waiters.WaitForFrame;

public class UpdateMotors extends Task {
    
    protected void run() {
        while (true) {
            Robot.motors.update();
            yield(new WaitForFrame());
        }
    }
}
