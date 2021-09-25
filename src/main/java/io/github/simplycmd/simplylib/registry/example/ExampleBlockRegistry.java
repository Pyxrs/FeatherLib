package io.github.simplycmd.simplylib.registry.example;

import io.github.simplycmd.simplylib.Main;
import io.github.simplycmd.simplylib.registry.*;
import io.github.simplycmd.simplylib.util.Util;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ExampleBlockRegistry {
    // Only use if you need a BlockItem
    public static final Block BLOCK = new Block(FabricBlockSettings.of(Material.STONE));

    public static void register() {
        RegisterModBlockCallback.EVENT.register(() -> {
            BlockRegistry.blocks.put(new BlockRegistrySettings(ID("simplylib_block")).itemModelType(BlockRegistrySettings.ItemModelType.NORMAL), BLOCK);

            BlockRegistry.blockItems.put(ID("simplylib_block"), new BlockItem(BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        });
    }

    private static ID ID(String id) {
        return new ID(Main.MOD_ID, id);
    }
}
