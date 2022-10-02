package com.simplycmd.featherlib;

import java.util.function.Consumer;

import com.simplycmd.featherlib.registry.RegistryHandler;

import net.devtech.arrp.api.RuntimeResourcePack;

public class Resources {
    @SuppressWarnings("deprecation")
    public static void addResource(Consumer<RuntimeResourcePack> pack) {
        pack.accept(RegistryHandler.RESOURCE_PACK);
    }
}
