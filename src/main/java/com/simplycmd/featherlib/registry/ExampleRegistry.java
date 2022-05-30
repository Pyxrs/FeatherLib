package com.simplycmd.featherlib.registry;

import com.simplycmd.featherlib.FeatherLib;
import com.simplycmd.featherlib.registry.BlockItem.ItemGroup;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;

//@Registry(modId = FeatherLib.MOD_ID)
public class ExampleRegistry {
    @BlockItem(maxCount = 23, group = ItemGroup.DECORATIONS)
    public static final Block EXAMPLE_BLOCK = new Block(FabricBlockSettings.of(Material.STONE));

    public static final Item EXAMPLE_ITEM = new Item(new FabricItemSettings().group(net.minecraft.item.ItemGroup.MATERIALS));
}