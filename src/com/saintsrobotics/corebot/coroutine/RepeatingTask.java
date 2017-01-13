package com.saintsrobotics.corebot.coroutine;

public abstract class RepeatingTask extends Task {
    
    @Override
    protected void run() {
        while (true) {
            doOnRepeat();
            yield(Wait.forFrame());
        }
    }
    
    protected abstract void doOnRepeat();
}
