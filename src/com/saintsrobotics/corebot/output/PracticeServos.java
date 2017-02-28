package com.saintsrobotics.corebot.output;

public class PracticeServos extends Servos {
    
    public PracticeServos() {
        // rightShifterPin, leftShifterPin
        super(9, 8);
    }
    
    @Override public int getRightShifterOut() { return 79; }
    @Override public int getRightShifterIn() { return 116; }
    @Override public int getLeftShifterOut() { return 76; }
    @Override public int getLeftShifterIn() { return 56; }
}
