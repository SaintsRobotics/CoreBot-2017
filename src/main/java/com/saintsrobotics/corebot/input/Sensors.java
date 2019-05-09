package com.saintsrobotics.corebot.input;

import com.saintsrobotics.corebot.input.sensors.Potentiometer;
import com.saintsrobotics.corebot.input.sensors.Ultrasound;

public abstract class Sensors {
    
    public final Ultrasound ultrasound;
    public final Potentiometer potentiometer;
    
    public Sensors(Ultrasound ultrasound, Potentiometer potentiometer) {
        this.ultrasound = ultrasound;
        this.potentiometer = potentiometer;
    }
    
    public void init() {
        ultrasound.init();
        potentiometer.init();
    }
    
    public void disable() {
        ultrasound.disable();
        potentiometer.disable();
    }
}
