package com.saintsrobotics.corebot.coroutine;

/**
 * Code to make writing a task that simply loops more convinent.
 * @author Shreyas Rama
 *
 */
public abstract class RepeatingTask extends Task {
    
    @Override
    protected void run() {
    	init();
    	long millisLast = System.currentTimeMillis();
    	wait.forFrame();
        while (true) {
            doOnRepeat(((double)(System.currentTimeMillis() - millisLast))/1000);
            millisLast = System.currentTimeMillis();
            wait.forFrame();
        }
    }
    /**
     * This function is repeatedly called, waiting for a frame in between each call.
     * Intended to be overridden with the desired behavior 
     * @param sec The number of seconds between this call and the last call.
     */
    protected abstract void doOnRepeat(double sec);
    /**
     * This function is run once when the Task first begins running.
     * Intended to be be overridden.
     */
    protected void init(){
    	
    }
}
