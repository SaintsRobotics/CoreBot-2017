package com.saintsrobotics.corebot.output;

public class TestBoardMotors extends Motors {
    
    @Override public double getGearDropOut() { return -1; }
    @Override public double getGearDropIn() { return -1; }
    
    public TestBoardMotors() {
        super(0, 2, 1, 3, 7, 6, 5, 4,
                false,
                false,
                false,
                false);
    }
}