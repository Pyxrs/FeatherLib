package io.github.simplycmd.featherlib.scheduler;

import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Consumer;

public class ServerScheduler {
    private static final Random RANDOM = new Random();
    private static HashMap<Integer, SchedulerInfoContainer> tasks = new HashMap<>();

    public static void registerEvent() {
        ServerTickCallback.EVENT.register((server) -> onTick());
    }

    protected static void onTick() {
        Iterator<Integer> iterator = tasks.keySet().iterator();
        while (iterator.hasNext()) {
            Integer task = iterator.next();
            SchedulerInfoContainer container = tasks.get(task);

            container.currentTick++;

            if (container.currentTick >= container.getMaxTicks()) {
                container.getAction().accept(task);
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
