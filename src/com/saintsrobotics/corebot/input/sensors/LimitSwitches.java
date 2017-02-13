package com.saintsrobotics.corebot.input.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

import java.util.ArrayList;
import java.util.List;

public class LimitSwitches {
    
    private List<LimitSwitch> switches = new ArrayList<>();
    
    public final LimitSwitch exampleSwitch;
    
    public LimitSwitches(int exampleSwitchPin) {
        exampleSwitch = new LimitSwitch(exampleSwitchPin);
        switches.add(exampleSwitch);
    }
    
    public void init() {
        switches.forEach(LimitSwitch::init);
    }
    
    public void disable() {
        switches.forEach(LimitSwitch::disable);
    }
    
    public static class LimitSwitch {
        
        private final int pin;
        private DigitalInput switchInput;
        
        LimitSwitch(int pin) {
            this.pin = pin;
        }
        
        private void init() {
            if (switchInput == null) switchInput = new DigitalInput(pin);
        }
        
        private void disable() {
            if (switchInput != null) {
                switchInput.free();
                switchInput = null;
            }
        }
        
        public boolean get() {
            return switchInput.get();
        }
    }
}
