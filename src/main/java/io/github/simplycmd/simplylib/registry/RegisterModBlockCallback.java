package io.github.simplycmd.simplylib.registry;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface RegisterModBlockCallback {
    Event<RegisterModBlockCallback> EVENT = EventFactory.createArrayBacked(RegisterModBlockCallback.class,
            (listeners) -> () -> {
                for (RegisterModBlockCallback listener : listeners) {
                    listener.register();
                }
            });

    void register();
}
