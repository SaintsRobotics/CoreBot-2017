package com.saintsrobotics.corebot.coroutine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The runner task for the coroutine framework. Initialize with a list of tasks, and iterates them until they finish yielding.
 * 
 * @author Benjamin Yin
 */
public class TaskRunner {
    
    private List<Task> tasks;
    private boolean disabled = false;
    /**
     * Initializes the TaskRunner with some tasks to run.
     * @param tasks The tasks you want this TaskRunner to iterate over.
     */
    public TaskRunner(Task... tasks) {
        this.tasks = new LinkedList<>();
        this.tasks.addAll(Arrays.asList(tasks));
    }
    /**
     * Loops once through all tasks, if the TaskRunner is not disabled.
     * On first run, Each Task should yield a BooleanSupplier. These are stored within the Task. 
     * On subsequent runs, run pools the BooleanSupplier. If it returns true, it asks for the next BooleanSupplier from the Task,
     * and stores it within the Task.
     * 
     * @see disable()
     */
    public void run() {
        if (disabled) {
            return;
        }
        //Equivalent to a standard iterator while pattern
        for (Iterator<Task> taskIterator = tasks.iterator(); taskIterator.hasNext(); ) {
            Task task = taskIterator.next();
            if (task.iterator == null) {
                task.iterator = task.iterator();
            }
            
            if (task.waiter != null && task.waiter.getAsBoolean()) {
                if (task.iterator.hasNext()) {
                    task.waiter = task.iterator.next();
                } else {
                    taskIterator.remove();
                }
            }
        }
    }
    /**
     * Permanently disable this TaskRunner; the TaskRunner's {@link run()} function should not do anything
     * once this function is called.
     */
    public void disable() {
        this.disabled = true;
    }
}