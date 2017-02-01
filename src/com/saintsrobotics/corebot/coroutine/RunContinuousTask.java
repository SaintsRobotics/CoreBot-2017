package com.saintsrobotics.corebot.coroutine;

public abstract class RunContinuousTask extends Task {
    
    @Override
    protected void run() {
        while (true) {
            runContinuously();
        }
    }

    /**
     * Runs this method in a while loop. Must wait inside.
     */
    protected abstract void runContinuously();
}
