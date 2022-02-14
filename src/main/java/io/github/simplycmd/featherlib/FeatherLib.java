package io.github.simplycmd.featherlib;

import io.github.simplycmd.featherlib.registry.Resources;
import io.github.simplycmd.featherlib.scheduler.ClientScheduler;
import io.github.simplycmd.featherlib.scheduler.ServerScheduler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class FeatherLib implements ModInitializer, ClientModInitializer {
    public static String MOD_ID = "featherlib";

    @Override
    public void onInitialize() {
        // Register resources
        Resources.register();

        // Register scheduler
        ServerScheduler.registerEvent();
    }

    @Override
    public void onInitializeClient() {
        // Register scheduler
        ClientScheduler.registerEvent();
    }
}
