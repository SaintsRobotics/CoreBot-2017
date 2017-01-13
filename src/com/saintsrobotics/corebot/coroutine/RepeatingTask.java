package com.saintsrobotics.corebot.coroutine;

public abstract class RepeatingTask extends Task {
    
    @Override
    protected void run() {
        while (true) {
            doOnRepeat();
            wait.forFrame();
        }
    }
    
    protected abstract void doOnRepeat();
}
