package com.saintsrobotics.corebot.input;

import com.saintsrobotics.corebot.input.sensors.LimitSwitches;
import com.saintsrobotics.corebot.input.sensors.Ultrasound;

public abstract class Sensors {
    
    public final LimitSwitches limitSwitches;
    public final Ultrasound ultrasound;
    public Sensors(LimitSwitches limitSwitches, Ultrasound ultrasound) {
        this.limitSwitches = limitSwitches;
        this.ultrasound = ultrasound;
    }
    
    public void init() {
        limitSwitches.init();
        ultrasound.init();
    }
    public void disable(){
    	limitSwitches.disable();
    	ultrasound.disable();
    }
}
