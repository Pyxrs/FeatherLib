package io.github.simplycmd.simplylib;

import java.util.ArrayList;

import io.github.simplycmd.simplylib.registry.example.ExampleBlockRegistry;
import io.github.simplycmd.simplylib.registry.example.ExampleItemRegistry;
import io.github.simplycmd.simplylib.scheduler.ClientScheduler;
import io.github.simplycmd.simplylib.scheduler.ServerScheduler;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer, ClientModInitializer {
    public static String MOD_ID = "simplylib";
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID + ":resource_pack");

    @Override
    public void onInitialize() {
        // ---------- Stuff only SimplyLib needs to handle ---------- //
        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
        ClientScheduler.registerEvent();
        ServerScheduler.registerEvent();

        // ---------- What you should do ---------- //
        // ExampleItemRegistry.register();
        // ExampleBlockRegistry.register();
    }

    @Override
    public void onInitializeClient() {
        // ---------- What you should do ---------- //

    }

    private static ArrayList<Runnable> tasks = new ArrayList<>();

    // Call this to add a delayed task
	public static void delay(Runnable task) {
		tasks.add(task);
	}

	public static void postInit() {
		// Runs after the normal fabric server is initialized
		for (Runnable task : tasks) {
			task.run();
		}
		tasks.clear();
	}
}
