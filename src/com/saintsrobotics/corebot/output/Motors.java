package com.saintsrobotics.corebot.output;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class Motors {
    private static Motor[] motors = new Motor[10];

    /**
     * This method attempts to return the speedcontroller corresponding with the pin number.
     * This will lazily construct motors, meaning that motors (and the associated actual speedcontroller) are only constructed when they are first gotten.
     * This method defaults to Talons when constructing new motors.
     * */

    public static Motor get(int pin, boolean inverted){
        if (motors[pin] == null) {
            motors[pin] = new Motor(pin, inverted);
        }
        return motors[pin];
    }
    public static void stopAll(){
        for(Motor m : motors) if(m!=null)m.stop();
    }
	public static void update(double sec) {
		for(Motor m : motors)  m.update(sec);
	}

}
