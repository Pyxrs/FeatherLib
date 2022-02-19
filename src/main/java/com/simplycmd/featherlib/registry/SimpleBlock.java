package com.simplycmd.featherlib.registry;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SimpleBlock {
    private final Block block;
    private final Identifier id;

    private Optional<BlockItem> item;

    public SimpleBlock(Identifier id, Block block) {
        this.block = block;
        this.id = id;
        this.item = Optional.empty();
        Registry.register(Registry.BLOCK, id, block);
    }

    public Identifier getId() {
        return id;
    }
    public Block getBlock() {
        return block;
    }
    public Optional<BlockItem> getItem() {
        return item;
    }

    public enum ItemModel {
        NONE,
        BLOCK,
        ITEM
    }

    public SimpleBlock withItem(ItemModel model, Function<Block, BlockItem> item) {
        this.item = Optional.of(item.apply(block));
        Registry.register(Registry.ITEM, id, this.item.get());
        switch (model) {
            case NONE:
                break;
            case BLOCK:
                Resources.registerBlockItemModel(id);
            case ITEM:
                Resources.registerTextureItemModel(id);
        }
        return this;
    }

    public SimpleBlock defaultBlockstate() {
        Resources.registerDefaultBlockstate(id);
        return this;
    }

    public SimpleBlock defaultLootTable() {
        Resources.registerDefaultBlockLootTable(id);
        return this;
    }
}
