package io.github.simplycmd.featherlib;

import io.github.simplycmd.featherlib.scheduler.ClientScheduler;
import io.github.simplycmd.featherlib.scheduler.ServerScheduler;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class FeatherLib implements ModInitializer, ClientModInitializer {
    public static String MOD_ID = "featherlib";
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID + ":resource_pack");

    @Override
    public void onInitialize() {
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));

        // Register scheduler
        ServerScheduler.registerEvent();
    }

    @Override
    public void onInitializeClient() {
        // Register scheduler
        ClientScheduler.registerEvent();
    }
}
