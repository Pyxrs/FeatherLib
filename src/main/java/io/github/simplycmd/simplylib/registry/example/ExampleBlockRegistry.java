package io.github.simplycmd.simplylib.registry.example;

import io.github.simplycmd.simplylib.Main;
import io.github.simplycmd.simplylib.registry.BlockRegistry;
import io.github.simplycmd.simplylib.registry.BlockRegistrySettings;
import io.github.simplycmd.simplylib.registry.ID;
import io.github.simplycmd.simplylib.registry.RegisterModBlockCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class ExampleBlockRegistry {
    // Method is called in Main (go there if you're confused)
    public static void register() {
        RegisterModBlockCallback.EVENT.register((blocks, block_items) -> {
            // Blocks
            blocks.put(new BlockRegistrySettings(ID("simplylib_block")), new Block(FabricBlockSettings.of(Material.STONE)));
            // Block Items
            block_items.put(ID("simplylib_block"), new BlockItem(get("simplylib_block"), new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        });

        BlockRegistry.register();
    }

    public static Block get(String blockId) {
        try {
            return BlockRegistry.get(ID(blockId));
        } catch (IllegalArgumentException i) {
            return Blocks.AIR;
        }
    }

    private static ID ID(String id) {
        // Your mod ID here
        return new ID(Main.MOD_ID, id);
    }
}