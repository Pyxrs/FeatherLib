package io.github.simplycmd.simplylib.registry;

import io.github.simplycmd.simplylib.Main;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {
    private static HashMap<BlockRegistrySettings, Block> blocks = new HashMap<>();
    private static HashMap<ID, BlockItem> blockItems = new HashMap<>();

    public static void register() {
        RegisterModBlockCallback.EVENT.invoker().register(blocks, blockItems);

        for (Map.Entry<BlockRegistrySettings, Block> block : blocks.entrySet()) {
            Registry.register(Registry.BLOCK, block.getKey().getId().getIdentifier(), block.getValue());
            Main.RESOURCE_PACK.addBlockState(ARRPUtil.blockstate(block.getKey().getId(), block.getKey().getBlockstateType()), block.getKey().getId().getIdentifier());
            Main.RESOURCE_PACK.addModel(ARRPUtil.model(block.getKey().getId(), block.getKey().getItemModelType()), new Identifier(block.getKey().getId().getNamespace(), "item/" + block.getKey().getId().getId()));
            Main.RESOURCE_PACK.addLootTable(new Identifier(block.getKey().getId().getNamespace(), "blocks/" + block.getKey().getId().getId()), ARRPUtil.blockLootTable(block.getKey().getId(), block.getKey().getLootType()));
        }
        for (Map.Entry<ID, BlockItem> item : blockItems.entrySet()) {
            Registry.register(Registry.ITEM, item.getKey().getIdentifier(), item.getValue());
        }
    }

    public static Block get(ID blockId) {
        // Needs to be a special iterator instead of getOrDefault() so that it correctly compares IDs
        if (blocks != null) {
            for (Map.Entry<BlockRegistrySettings, Block> block : blocks.entrySet()) {
                if (checkIfIDsEqual(blockId, block.getKey().getId())) {
                    return block.getValue();
                }
            }
            return Blocks.AIR;
        }
        return Blocks.AIR;
    }

    public static BlockItem getBlockItem(ID itemId) {
        if (blockItems != null) {
            for (Map.Entry<ID, BlockItem> item : blockItems.entrySet()) {
                if (checkIfIDsEqual(itemId, item.getKey())) {
                    return item.getValue();
                }
            }
            return (BlockItem) Blocks.AIR.asItem();
        }
        return (BlockItem) Blocks.AIR.asItem();
    }

    public static boolean checkIfIDsEqual(ID id1, ID id2) {
        if (id1.getId().matches(id2.getId()))
            return id1.getNamespace().matches(id2.getNamespace());
        return false;
    }
}
