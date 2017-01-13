package com.saintsrobotics.corebot.coroutine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class TaskRunner {
    
    private List<Task> tasks;
    private boolean disabled = false;
    
    public TaskRunner(Task[] tasks) {
        this.tasks = new LinkedList<>();
        this.tasks.addAll(Arrays.asList(tasks));
    }
    
    public void run() {
        if (disabled) return;
        Iterator<Task> list = tasks.iterator();
        while (list.hasNext()) {
            Task task = list.next();
            if (task.iterator == null) task.iterator = task.iterator();
            if (task.waiter != null && task.waiter.getAsBoolean())
                if (task.iterator.hasNext())
                    task.waiter = task.iterator.next();
                else
                    list.remove();
        }
    }
    
    public void disable() {
        this.disabled = true;
    }
}