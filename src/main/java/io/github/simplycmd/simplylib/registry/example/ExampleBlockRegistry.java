package io.github.simplycmd.simplylib.registry.example;

import io.github.simplycmd.simplylib.Main;
import io.github.simplycmd.simplylib.registry.*;
import io.github.simplycmd.simplylib.util.Util;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ExampleBlockRegistry {
    public static void register() {
        RegisterModBlockCallback.EVENT.register((blocks, block_items) -> {
            blocks.put(new BlockRegistrySettings(ID("simplylib_block")).itemModelType(BlockRegistrySettings.ItemModelType.NORMAL), new Block(FabricBlockSettings.of(Material.STONE)));

            block_items.put(ID("simplylib_block"), new SimplyLibBlockItem(ID("simplylib_block"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        });
    }

    private static ID ID(String id) {
        return new ID(Main.MOD_ID, id);
    }
}
