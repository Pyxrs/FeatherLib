package io.github.simplycmd.simplylib.registry;

import io.github.simplycmd.simplylib.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {
    private static HashMap<BlockRegistrySettings, Block> blocks = new HashMap<>();
    private static HashMap<String, BlockItem> blockItems = new HashMap<>();


    public static void register() {
        RegisterModBlockCallback.EVENT.invoker().register(blocks, blockItems);

        // Scary code do not touch
        for (Map.Entry<BlockRegistrySettings, Block> block : blocks.entrySet()) {
            Registry.register(Registry.BLOCK, Main.ID(block.getKey().getId()), block.getValue());
            Main.RESOURCE_PACK.addBlockState(ARRPUtil.blockstate(block.getKey().getId(), block.getKey().getBlockstateType()), Main.ID(block.getKey().getId()));
            Main.RESOURCE_PACK.addModel(ARRPUtil.model(block.getKey().getId(), block.getKey().getItemModelType()), Main.ID("item/" + block.getKey().getId()));
            Main.RESOURCE_PACK.addLootTable(Main.ID(block.getKey().getId()), ARRPUtil.lootTable(block.getKey().getId(), block.getKey().getLootType()));
        }
        for (Map.Entry<String, BlockItem> item : blockItems.entrySet()) {
            Registry.register(Registry.ITEM, Main.ID(item.getKey()), item.getValue());
        }
    }

    public static Block get(String blockId) {
        if (blocks != null) {
            for (Map.Entry<BlockRegistrySettings, Block> block : blocks.entrySet()) {
                if (block.getKey().getId().matches(blockId)) {
                    return block.getValue();
                }
            }
            throw new IllegalArgumentException("Block not valid!");
        } else {
            return Blocks.AIR;
        }
    }

    public static BlockItem getBlockItem(String itemId) {
        if (blockItems != null) {
            for (Map.Entry<String, BlockItem> item : blockItems.entrySet()) {
                if (item.getKey().matches(itemId)) {
                    return item.getValue();
                }
            }
            throw new IllegalArgumentException("Block not valid!");
        } else {
            return (BlockItem) Blocks.AIR.asItem();
        }
    }
}
