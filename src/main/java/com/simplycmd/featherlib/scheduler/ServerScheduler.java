package com.simplycmd.featherlib.scheduler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;

public class ServerScheduler implements ModInitializer {
    private static HashMap<UUID, SchedulerInfoContainer> tasks = new HashMap<>();
    
    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> onTick());
    }

    protected static void onTick() {
        Iterator<UUID> iterator = tasks.keySet().iterator();
        while (iterator.hasNext()) {
            UUID task = iterator.next();
            SchedulerInfoContainer container = tasks.get(task);

            container.currentTick++;

            if (container.currentTick >= container.getMaxTicks()) {
                container.getAction().accept(task);
                iterator.remove();
            }
        }
    }

    public static void schedule(int tickDelay, Consumer<UUID> action) {
        tasks.put(UUID.randomUUID(), new SchedulerInfoContainer(tickDelay, action));
    }

    
}
