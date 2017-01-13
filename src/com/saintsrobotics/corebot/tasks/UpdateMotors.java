package com.saintsrobotics.corebot.tasks;


import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;

public class UpdateMotors extends Task {
    
    protected void run() {
        while (true) {
            Robot.motors.update();
            wait.forFrame();
        }
    }
}
