package com.saintsrobotics.corebot.coroutine;

public abstract class RunContinuousTask extends Task {
    
    @Override
    protected void run() {
        while (true) {
            runContinuously();
        }
    }
    
    protected abstract void runContinuously();
}
