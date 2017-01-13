package com.saintsrobotics.corebot.coroutine.waiters;

import java.util.function.BooleanSupplier;

public class WaitForSeconds implements BooleanSupplier {
    
    private long finalTimeMillis;
    
    public WaitForSeconds(double sec) {
        finalTimeMillis = (long) (sec * 1000) + System.currentTimeMillis();
    }
    
    @Override
    public boolean getAsBoolean() {
        return finalTimeMillis < System.currentTimeMillis();
    }
    
}
