package com.simplycmd.featherlib.registry;

import com.simplycmd.featherlib.FeatherLib;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.block.Material;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ExampleRegistry extends SimpleRegistry {
    private static final SimpleBlock[] blocks = {
        new SimpleBlock(ID("example_block"), new Block(FabricBlockSettings.of(Material.STONE)),
            (block) -> new BlockItem(block, new FabricItemSettings()),
            (block, model) -> {
                model.registerSimpleCubeAll(block);
                model.registerParentedItemModel(block);
            }
        ),
    };
    private static final SimpleItem[] items = {
        new SimpleItem(ID("example_item"), new Item(new FabricItemSettings()), (item, model) -> model.register(item, Models.GENERATED)),
    };

    public ExampleRegistry() {
        super(blocks, items);
    }

    public static Identifier ID(String id) {
        return new Identifier(FeatherLib.MOD_ID, id);
    }
}