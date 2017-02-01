package com.saintsrobotics.corebot.coroutine;

public abstract class RunEachFrameTask extends Task {

    @Override
    protected void run() {
        while (true) {
            runEachFrame();
            wait.forFrame();
        }
    }

    protected abstract void runEachFrame();
}
