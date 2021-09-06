package io.github.simplycmd.simplylib.scheduler;

import net.fabricmc.fabric.api.event.client.ClientTickCallback;

public class ClientScheduler extends Scheduler {
    public static void registerEvent() {
        ClientTickCallback.EVENT.register((client) -> onTick());
    }
}
