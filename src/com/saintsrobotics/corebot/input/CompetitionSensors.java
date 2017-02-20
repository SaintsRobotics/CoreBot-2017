package com.saintsrobotics.corebot.input;

import com.saintsrobotics.corebot.input.sensors.Potentiometer;
import com.saintsrobotics.corebot.input.sensors.Ultrasound;

public class CompetitionSensors extends Sensors {
    
    public CompetitionSensors() {
        super(new Ultrasound(1), new Potentiometer(3));
    }
}
