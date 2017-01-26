package com.saintsrobotics.corebot.input;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;

public abstract class Sensors {
    
    public final LimitSwitches limitSwitches;
    public AnalogGyro gyro;
	public AnalogInput ultra;
    
    public Sensors(LimitSwitches limitSwitches) {
        this.limitSwitches = limitSwitches;
    }
    
    public void init() {
        limitSwitches.init();
        gyro = new AnalogGyro(0);
        ultra = new AnalogInput(1);
    }
}
