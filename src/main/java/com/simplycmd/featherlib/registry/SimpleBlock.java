package com.simplycmd.featherlib.registry;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SimpleBlock implements ItemConvertible {
    @Getter
    private final Identifier id;
    @Getter
    private final Block block;
    private final Optional<Function<Block, BlockItem>> item;

    protected final Optional<BiConsumer<Block, BlockStateModelGenerator>> resources;
    
    public SimpleBlock(Identifier id, Block block) {
        this.id = id;
        this.block = block;
        this.resources = Optional.empty();
        this.item = Optional.empty();
    }
    public SimpleBlock(Identifier id, Block block, BiConsumer<Block, BlockStateModelGenerator> resources) {
        this.id = id;
        this.block = block;
        this.resources = Optional.of(resources);
        this.item = Optional.empty();
    }
    public SimpleBlock(Identifier id, Block block, Function<Block, BlockItem> item, Optional<BiConsumer<Block, BlockStateModelGenerator>> resources) {
        this.id = id;
        this.block = block;
        this.resources = resources;
        this.item = Optional.of(item);
    }

    protected void register() {
        Registry.register(Registry.BLOCK, id, block);
        if (item.isPresent())
            Registry.register(Registry.ITEM, id, item.get().apply(block));
    }

    @Override
    public Item asItem() {
        if (item.isPresent())
            return item.get().apply(block);
        return block.asItem();
    }
}
