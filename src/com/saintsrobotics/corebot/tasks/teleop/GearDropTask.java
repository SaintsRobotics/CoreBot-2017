package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.util.PID;

public class GearDropTask extends Task {
    
    @Override
    protected void run() {
        PID armPid = new PID(0.01, 0, 0);
        while (true) {
            wait.until(() -> Robot.oi.drive.buttons.RB());
            
            
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < startTime + 500) {
                double value = armPid.compute(Robot.sensors.potentiometer.get(), 45.0);
                Robot.motors.gearDrop.set(value);
                wait.forFrame();
            }
            while (System.currentTimeMillis() < startTime + 1000) {
                double value = armPid.compute(Robot.sensors.potentiometer.get(), 0);
                Robot.motors.gearDrop.set(value);
                wait.forFrame();
            }
        }
    }
}
