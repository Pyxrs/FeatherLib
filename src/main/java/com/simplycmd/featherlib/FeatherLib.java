package com.simplycmd.featherlib;

import com.simplycmd.featherlib.scheduler.ClientScheduler;
import com.simplycmd.featherlib.scheduler.ServerScheduler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class FeatherLib implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "featherlib";

    @Override
    public void onInitialize() {
        ServerScheduler.registerEvent();
    }

    @Override
    public void onInitializeClient() {
        ClientScheduler.registerEvent();
    }
}
