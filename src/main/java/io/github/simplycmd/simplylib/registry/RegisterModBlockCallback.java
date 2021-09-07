package io.github.simplycmd.simplylib.registry;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface RegisterModBlockCallback {
    Event<RegisterModBlockCallback> EVENT = EventFactory.createArrayBacked(RegisterModBlockCallback.class,
            (listeners) -> (blocks, block_items) -> {
                for (RegisterModBlockCallback listener : listeners) {
                    listener.register(blocks, block_items);
                }
            });

    void register(HashMap<BlockRegistrySettings, Block> blocks, HashMap<ID, SimplyLibBlockItem> block_items);
}
