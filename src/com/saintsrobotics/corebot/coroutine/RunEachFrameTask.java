package com.saintsrobotics.corebot.coroutine;

public abstract class RunEachFrameTask extends Task {

    @Override
    protected void run() {
        while (true) {
            runEachFrame();
            wait.forFrame();
        }
    }

    /**
     * Runs this method once per frame. Don't wait inside.
     */
    protected abstract void runEachFrame();
}
