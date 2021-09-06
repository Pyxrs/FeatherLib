package io.github.simplycmd.simplylib;

import io.github.simplycmd.simplylib.registry.BlockRegistry;
import io.github.simplycmd.simplylib.registry.ItemRegistry;

public class PostMain {
    public static void onPostInitialize() {
        ItemRegistry.register();
        BlockRegistry.register();
    }
}