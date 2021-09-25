package io.github.simplycmd.simplylib.registry;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface RegisterModItemCallback {
    Event<RegisterModItemCallback> EVENT = EventFactory.createArrayBacked(RegisterModItemCallback.class,
            (listeners) -> () -> {
                for (RegisterModItemCallback listener : listeners) {
                    listener.register();
                }
            });

    void register();
}
