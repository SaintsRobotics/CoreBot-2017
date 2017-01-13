package com.saintsrobotics.corebot.input;

public abstract class Sensors {
    
    public final LimitSwitches limitSwitches;
    
    public Sensors(LimitSwitches limitSwitches) {
        this.limitSwitches = limitSwitches;
    }
    
    public void init() {
        limitSwitches.init();
    }
}
