package com.saintsrobotics.corebot.coroutine;

import com.zoominfo.util.yieldreturn.Generator;

import java.util.Iterator;
import java.util.function.BooleanSupplier;

public abstract class Task extends Generator<BooleanSupplier> {
    
    protected Waiters wait = new Waiters();
    
    BooleanSupplier waiter = () -> true;
    Iterator<BooleanSupplier> iterator;
    
    protected class Waiters {
        public void forFrame() {
            yield(() -> true);
        }
    
        public void forSeconds(double secs) {
            long finalTimeMillis = (long) (secs * 1000) + System.currentTimeMillis();
            yield(() -> finalTimeMillis < System.currentTimeMillis());
        }
    
        public void until(BooleanSupplier predicate) {
            yield(predicate);
        }

        public void untilWithTimeout(BooleanSupplier predicate, double secs) {
            long finalTimeMillis = (long) (secs * 1000) + System.currentTimeMillis();
            yield(() -> predicate.getAsBoolean() || finalTimeMillis < System.currentTimeMillis());
        }
    }
}