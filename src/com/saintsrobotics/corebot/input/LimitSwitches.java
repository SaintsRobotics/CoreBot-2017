package com.saintsrobotics.corebot.input;

import edu.wpi.first.wpilibj.DigitalInput;

import java.util.ArrayList;
import java.util.List;

public class LimitSwitches {
    
    private List<LimitSwitch> switches = new ArrayList<>();
    
    public final LimitSwitch exampleSwitch;
    
    LimitSwitches(int exampleSwitchPin) {
        exampleSwitch = new LimitSwitch(exampleSwitchPin);
        switches.add(exampleSwitch);
    }
    
    void init() {
        switches.forEach(LimitSwitch::init);
    }
    
    public static class LimitSwitch {
        
        private final int pin;
        private DigitalInput switchInput;
    
        LimitSwitch(int pin) {
            this.pin = pin;
        }
        
        private void init() {
            switchInput = new DigitalInput(pin);
        }
        
        public boolean get() {
            return switchInput.get();
        }
    }
}
