package io.github.simplycmd.simplylib.scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public abstract class Scheduler {
    private static final Random RANDOM = new Random();
    private static HashMap<Integer, SchedulerInfoContainer> tasks = new HashMap<>();

    public static void registerEvent() {
    }

    protected static void onTick() {
        Iterator<Map.Entry<Integer, SchedulerInfoContainer>> iterator = tasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, SchedulerInfoContainer> task = iterator.next();
            SchedulerInfoContainer container = task.getValue();

            container.currentTick++;

            if (container.currentTick >= container.getMaxTicks()) {
                container.getAction().accept(task.getKey());
                iterator.remove();
            }
        }
    }

    public static void schedule(int tickDelay, Consumer<Integer> action) {
        int id = RANDOM.nextInt();
        while (tasks.containsKey(id)) id = RANDOM.nextInt();

        tasks.put(id, new SchedulerInfoContainer(tickDelay, action));
    }
}
