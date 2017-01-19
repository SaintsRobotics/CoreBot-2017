package com.saintsrobotics.corebot.coroutine;

import com.zoominfo.util.yieldreturn.Generator;

import java.util.Iterator;
import java.util.function.BooleanSupplier;

public abstract class Task extends Generator<BooleanSupplier> {
    
    protected Waiters wait = new Waiters();
    /**
     * This BooleanSupplier is called repeatedly to determine if execution of the Task should resume
     */
    BooleanSupplier waiter = () -> true;
    Iterator<BooleanSupplier> iterator;
    
    /**
     * This class makes it cleaner to create waiters
     * @author Shreyas Raman
     */
    protected class Waiters {
    	/**
    	 * Yield for one TaskRunner run cycle; Allows input and new motor values to update.
    	 */
        public void forFrame() {
            yield(() -> true);
        }
        /**
         * Yield for a given number of seconds
         * @param secs Seconds to wait for
         */
        public void forSeconds(double secs) {
            long finalTimeMillis = (long) (secs * 1000) + System.currentTimeMillis();
            yield(() -> finalTimeMillis < System.currentTimeMillis());
        }
        /**
         * Yield for a custom BooleanSupplier; Code will not resume until this BooleanSupplier returns true.
         * @param predicate BooleanSupplier for TaskRunner to poll. 
         */
    
        public void until(BooleanSupplier predicate) {
            yield(predicate);
        }
    }
}