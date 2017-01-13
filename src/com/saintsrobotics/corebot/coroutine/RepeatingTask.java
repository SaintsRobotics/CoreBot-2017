package com.saintsrobotics.corebot.coroutine;

import com.saintsrobotics.corebot.coroutine.waiters.WaitForFrame;

public abstract class RepeatingTask extends Task {
    
    @Override
    protected void run() {
        while (true) {
            doOnRepeat();
            yield(new WaitForFrame());
        }
    }
    
    protected abstract void doOnRepeat();
}
