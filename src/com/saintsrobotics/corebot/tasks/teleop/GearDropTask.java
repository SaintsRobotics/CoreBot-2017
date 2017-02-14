package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunContinuousTask;
import com.saintsrobotics.corebot.util.PID;

public class GearDropTask extends RunContinuousTask {
	PID armPid = new PID(Robot.prefs.getDouble("P value", 0.1),
		Robot.prefs.getDouble("I value", 1.0),
		Robot.prefs.getDouble("D value", 1.0));

	@Override
	protected void runContinuously() {
		wait.until(() -> Robot.oi.drive.buttons.RB());
        long startTime = System.currentTimeMillis();
        
        while (System.currentTimeMillis() < startTime + 500) {
            double value = armPid.compute(Robot.sensors.potentiometer.get(), Robot.prefs.getDouble("Top Gear Drop Angle", 45.0));
            Robot.motors.gearDrop.set(value);
            wait.forFrame();
        }
        
        while (System.currentTimeMillis() < startTime + 1000) {
            double value = armPid.compute(Robot.sensors.potentiometer.get(), Robot.prefs.getDouble("Bottom Gear Drop Angle", 0));
            Robot.motors.gearDrop.set(value);
            wait.forFrame();
        }

	}
}
