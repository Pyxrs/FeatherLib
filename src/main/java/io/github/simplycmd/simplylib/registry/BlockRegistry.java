package io.github.simplycmd.simplylib.registry;

import io.github.simplycmd.simplylib.Main;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JPosition;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static net.devtech.arrp.api.RuntimeResourcePack.id;
import static net.devtech.arrp.json.loot.JLootTable.*;

public class BlockRegistry {
    private static HashMap<BlockRegistrySettings, Block> blocks = new HashMap<>();
    private static HashMap<String, BlockItem> blockItems = new HashMap<>();


    public static void register() {
        RegisterModBlockCallback.EVENT.invoker().register(blocks, blockItems);

        // Scary code do not touch
        for (Map.Entry<BlockRegistrySettings, Block> block : blocks.entrySet()) {
            Registry.register(Registry.BLOCK, Main.ID(block.getKey().getId()), block.getValue());
            Main.RESOURCE_PACK.addBlockState(blockstate(block.getKey().getId(), block.getKey().getBlockstateType()), Main.ID(block.getKey().getId()));
            Main.RESOURCE_PACK.addModel(model(block.getKey().getId(), block.getKey().getItemModelType()), Main.ID("item/" + block.getKey().getId()));
            lootTable(block.getKey().getId(), block.getKey().getLootType());
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

    public static void lootTable(String blockId, BlockRegistrySettings.LootType type) {
        switch (type) {
            case NORMAL: {
                Main.RESOURCE_PACK.addLootTable(id(Main.MOD_ID + ":blocks/" + blockId),
                        loot("minecraft:block")
                                .pool(pool()
                                        .rolls(1)
                                        .entry(entry()
                                                .type("minecraft:item")
                                                .name(Main.MOD_ID + ":" + blockId))
                                        .condition(predicate("minecraft:survives_explosion")))
                );
            }
            case NONE: {

            }
        }
    }

    private static JState blockstate(String blockId, BlockRegistrySettings.BlockstateType type) {
        JBlockModel model = JState.model(Main.MOD_ID + ":block/" + blockId);
        return switch (type) {
            case RANDOM_X -> JState.state(JState.multipart(
                    model, model.x(90), model.x(180), model.x(270)
            ));
            case RANDOM_Y -> JState.state(JState.multipart(
                    model, model.y(90), model.y(180), model.y(270)
            ));
            case RANDOM -> JState.state(JState.multipart(
                    model, model.x(90), model.x(180), model.x(270), model.y(90), model.y(180), model.y(270)
            ));
            default -> JState.state(JState.variant(model));
        };
    }

    private static JModel model(String blockId, BlockRegistrySettings.ItemModelType type) {
        switch (type) {
            case TEXTURE:
                return JModel.model().parent("minecraft:item/generated").textures(JModel.textures().layer0(Main.MOD_ID + ":item/" + blockId));
            default:
                return JModel.model().parent(Main.MOD_ID + ":block/" + blockId).display(JModel.display()
                        .setGui(new JPosition().rotation(30, 45, 0).scale(0.625f, 0.625f, 0.625f))
                        .setGround(new JPosition().translation(0, 3, 0).scale(0.25f, 0.25f, 0.25f))
                        .setHead(new JPosition().rotation(0, 180, 0).scale(1, 1, 1))
                        .setFixed(new JPosition().rotation(0, 180, 0).scale(0.5f, 0.5f, 0.5f))
                        .setThirdperson_righthand(new JPosition().rotation(75, 315, 0).translation(0, 2.5f, 0).scale(0.375f, 0.375f, 0.375f))
                        .setFirstperson_righthand(new JPosition().rotation(0, 315, 0).scale(0.4f, 0.4f, 0.4f))
                );
        }
    }
}
