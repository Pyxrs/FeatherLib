package io.github.simplycmd.simplylib.scheduler;

import java.util.HashMap;
import java.util.Map;

public abstract class Scheduler {
    private static HashMap<Runnable, Integer> tasksMax = new HashMap<>();
    private static HashMap<Runnable, Integer> tasks = new HashMap<>();

    public static void registerEvent() {
    }

    protected static void onTick() {
        for (Map.Entry<Runnable, Integer> task : tasksMax.entrySet()) {
            Runnable action = task.getKey();
            int currentTick = tasks.get(action);

            tasks.replace(action, currentTick + 1);
            currentTick = tasks.get(action);

            if (currentTick >= task.getValue()) {
                action.run();
                tasksMax.remove(action);
                tasks.remove(action);
            }
        }
    }

    public static void schedule(int tickDelay, Runnable action) {
        tasks.put(action, 0);
        tasksMax.put(action, tickDelay);
    }
}
