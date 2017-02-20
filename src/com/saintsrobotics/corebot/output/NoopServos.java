package com.saintsrobotics.corebot.output;

public class NoopServos extends Servos {
    
    @Override public int getRightShifterOut() { return 0; }
    @Override public int getRightShifterIn() { return 0; }
    @Override public int getLeftShifterOut() { return 0; }
    @Override public int getLeftShifterIn() { return 0; }
    
    public NoopServos() {
        super(-1, -1);
    }
}
