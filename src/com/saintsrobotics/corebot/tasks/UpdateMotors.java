package com.saintsrobotics.corebot.tasks;


import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.Wait;

public class UpdateMotors extends Task {
    
    protected void run() {
        while (true) {
            Robot.motors.update();
            yield(Wait.forFrame());
        }
    }
}
