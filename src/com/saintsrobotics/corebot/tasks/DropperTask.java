package com.saintsrobotics.corebot.tasks;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;

import com.saintsrobotics.corebot.PID;

public class DropperTask extends Task {

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
	    PID armPid = new PID(0.01, 0, 0);
        while (true) {
			wait.until(() -> Robot.oi.drive.buttons.A());
			
	        long startTime = System.currentTimeMillis();
	        while (System.currentTimeMillis() < startTime + 500) { 
	        	armPid.compute(Robot.sensors.potentiometer.get(), 45.0);
	        }
	        while (System.currentTimeMillis() < startTime + 1000) { 
	        	armPid.compute(Robot.sensors.potentiometer.get(), 0);
	        }
        }
	}
}
