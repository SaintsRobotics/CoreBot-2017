package com.saintsrobotics.corebot.tasks;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.input.sensors.Potentiometer;

import com.saintsrobotics.corebot.PID;

public class DropperTask extends Task {

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
		double armPos = 0.5;
		Potentiometer p = new Potentiometer();
	    PID armPid = new PID(0.01, 0, 0);
        armPos = Math.max(armPos, 0);
        while (true) {
	        double armVal = armPid.compute(p.get(), armPos);
			wait.until(() -> Robot.oi.drive.buttons.A());
			System.currentTimeMillis();
	        Robot.motors.gearDrop.set(armVal);
	       while (!(System.currentTimeMillis() 
	        Robot.motors.gearDrop.set(-armVal);
        }
	}
}
