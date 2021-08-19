package io.github.simplycmd.simplylib;

import io.github.simplycmd.simplylib.registry.BlockRegistry;
import io.github.simplycmd.simplylib.registry.ItemRegistry;
import io.github.simplycmd.simplylib.registry.example.ExampleBlockRegistry;
import io.github.simplycmd.simplylib.registry.example.ExampleItemRegistry;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
    public static String MOD_ID = "simplylib";
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID + ":resource_pack");

    public static Identifier ID(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
        ExampleItemRegistry.register();
        ExampleBlockRegistry.register();
        ItemRegistry.register();
        BlockRegistry.register();
    }
}
