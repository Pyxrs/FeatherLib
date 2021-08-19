package io.github.simplycmd.simplylib.scheduler;

import net.fabricmc.fabric.api.event.server.ServerTickCallback;

public class ServerScheduler extends Scheduler {
    public static void registerEvent() {
        ServerTickCallback.EVENT.register((server) -> onTick());
    }
}
