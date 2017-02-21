package com.saintsrobotics.corebot.input;

import com.saintsrobotics.corebot.input.sensors.Potentiometer;
import com.saintsrobotics.corebot.input.sensors.Ultrasound;

public class PracticeSensors extends Sensors {
    
    public PracticeSensors() {
        super(new Ultrasound(0), new Potentiometer(3));
    }
}
