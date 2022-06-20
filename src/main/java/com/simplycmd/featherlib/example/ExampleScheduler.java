package com.simplycmd.featherlib.example;

import com.simplycmd.featherlib.scheduler.ClientScheduler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ExampleScheduler implements ClientModInitializer {
    public static void runInTwentySeconds() {
        // ServerScheduler only runs when the world starts, while ClientScheduler runs when the client window launches
        ClientScheduler.schedule(400, (id) -> {

            // id is usually useless, it just tells you the UUID your event was stored under.
            System.out.println("This code has been run twenty seconds after it was scheduled. Only printed in development environment! Event id: " + id);
        });
    }

    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) runInTwentySeconds();
    }
}
