package io.github.simplycmd.simplylib;

import io.github.simplycmd.simplylib.registry.example.ExampleItemRegistry;
import io.github.simplycmd.simplylib.scheduler.ClientScheduler;
import io.github.simplycmd.simplylib.scheduler.ServerScheduler;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer, ClientModInitializer {
    public static String MOD_ID = "simplylib";
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID + ":resource_pack");

    public static Identifier ID(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        // ---------- Stuff only SimplyLib needs to handle ---------- //
        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
        ClientScheduler.registerEvent();
        ServerScheduler.registerEvent();

        // ---------- What you should do ---------- //
        //ExampleItemRegistry.register();
        //ExampleBlockRegistry.register();
    }

    @Override
    public void onInitializeClient() {
    }
}
