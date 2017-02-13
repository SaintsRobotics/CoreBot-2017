package com.saintsrobotics.corebot.input.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasound {
    
    private AnalogInput analog;
    private int pin;
    
    public Ultrasound(int pin) {
        this.pin = pin;
    }
    
    public void init() {
        if (analog == null) analog = new AnalogInput(pin);
    }
    
    public void disable() {
        if (analog != null) {
            analog.free();
            analog = null;
        }
    }
    
    public double getDistance() {
        return analog.getAverageVoltage() * 53.72 - 5.62;
    }
}
