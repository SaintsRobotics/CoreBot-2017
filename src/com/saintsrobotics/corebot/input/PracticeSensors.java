package com.saintsrobotics.corebot.input;

import com.saintsrobotics.corebot.input.sensors.LimitSwitches;
import com.saintsrobotics.corebot.input.sensors.Potentiometer;
import com.saintsrobotics.corebot.input.sensors.Ultrasound;

public class PracticeSensors extends Sensors {
    
    public PracticeSensors() {
        super(new LimitSwitches(0), new Ultrasound(1), new Potentiometer(2));
    }
}
