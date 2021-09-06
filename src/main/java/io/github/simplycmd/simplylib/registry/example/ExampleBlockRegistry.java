package io.github.simplycmd.simplylib.registry.example;

import io.github.simplycmd.simplylib.registry.BlockRegistry;
import io.github.simplycmd.simplylib.registry.BlockRegistrySettings;
import io.github.simplycmd.simplylib.registry.RegisterModBlockCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class ExampleBlockRegistry {
    public static void register() {
        RegisterModBlockCallback.EVENT.register((blocks, block_items) -> {
            blocks.put(new BlockRegistrySettings("simplylib_block", null, null, null), new Block(FabricBlockSettings.of(Material.STONE)));

            block_items.put("simplylib_block", new BlockItem(BlockRegistry.get("simplylib_block"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        });
    }
}
