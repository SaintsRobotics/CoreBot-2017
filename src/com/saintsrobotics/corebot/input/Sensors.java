package com.saintsrobotics.corebot.input;

import com.saintsrobotics.corebot.input.sensors.LimitSwitches;
import com.saintsrobotics.corebot.input.sensors.Potentiometer;
import com.saintsrobotics.corebot.input.sensors.Ultrasound;

public abstract class Sensors {
    
    public final LimitSwitches limitSwitches;
    public final Ultrasound ultrasound;
    public final Potentiometer potentiometer;
    
    public Sensors(LimitSwitches limitSwitches, Ultrasound ultrasound, Potentiometer potentiometer) {
        this.limitSwitches = limitSwitches;
        this.ultrasound = ultrasound;
        this.potentiometer = potentiometer;
    }
    
    public void init() {
        if (limitSwitches != null) limitSwitches.init();
        ultrasound.init();
        potentiometer.init();
    }
    
    public void disable() {
        if (limitSwitches != null) limitSwitches.disable();
        ultrasound.disable();
        potentiometer.disable();
    }
}
