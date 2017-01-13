package com.saintsrobotics.corebot.coroutine;

import java.util.function.BooleanSupplier;

public class Wait {
    
    public static BooleanSupplier forFrame() {
        return () -> true;
    }
    
    public static BooleanSupplier forSeconds(double secs) {
        long finalTimeMillis = (long) (secs * 1000) + System.currentTimeMillis();
        return () -> finalTimeMillis < System.currentTimeMillis();
    }
    
    public static BooleanSupplier forPredicate(BooleanSupplier predicate) {
        return predicate;
    }
}
