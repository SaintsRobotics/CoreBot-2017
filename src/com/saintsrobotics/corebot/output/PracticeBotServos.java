package com.saintsrobotics.corebot.output;

public class PracticeBotServos extends Servos {
    
    public PracticeBotServos() {
        // rightShifterPin, leftShifterPin
        super(9, 8);
    }
    
    @Override public int getRightShifterOut() { return 79; }
    @Override public int getRightShifterIn() { return 116; }
    @Override public int getLeftShifterOut() { return 76; }
    @Override public int getLeftShifterIn() { return 56; }
}
