package com.simplycmd.featherlib.registry;

import java.lang.reflect.Field;
import java.util.UUID;

import com.simplycmd.featherlib.FeatherLib;

import org.atteo.classindex.ClassIndex;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryHandler implements ModInitializer {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(new Identifier(FeatherLib.MOD_ID + "_mods", "resources"));
    private static final UUID BLOCK_ITEM_UUID = UUID.randomUUID();

    @Override
    public void onInitialize() {
        for (var clazz : ClassIndex.getAnnotated(com.simplycmd.featherlib.registry.Registry.class)) {
            final var modId = clazz.getAnnotation(com.simplycmd.featherlib.registry.Registry.class).modId();
            for (var field : clazz.getDeclaredFields()) {
                initialize(modId, clazz, field);
            }
        }
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
    }

    private void initialize(String modId, Class<?> clazz, Field field) {
        try {
            // Send all declared fields to be registered
            final var name = field.getName().toLowerCase();
            var value = field.get(clazz);
            register(modId, name, value);

            // BlockItem annotation handler
            if (value instanceof Block) {
                final var bi = (com.simplycmd.featherlib.registry.BlockItem)field.getAnnotation(com.simplycmd.featherlib.registry.BlockItem.class);
                if (bi != null) register(modId, name, new BlockItem((Block)field.get(clazz), new FabricItemSettings()
                    .group(bi.group().getGroup())
                    .rarity(bi.rarity())
                    .maxDamage(bi.maxDamage())
                    .maxCount(bi.maxCount())
                ));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void register(String modId, String objectId, Object object) {
        if (object instanceof Item) {
            final var id = new Identifier(modId, "item/" + objectId);
            Registry.register(Registry.ITEM, new Identifier(modId, objectId.replaceAll(BLOCK_ITEM_UUID.toString(), "")), (Item) object);
            RESOURCE_PACK.addModel(JModel.model("minecraft:item/generated").textures(new JTextures().layer0(modId + ":item/" + objectId)), id);
        }
        if (object instanceof Block) {
            Registry.register(Registry.BLOCK, new Identifier(modId, objectId), (Block) object);
            RESOURCE_PACK.addBlockState(JState.state(new JVariant().put("", JState.model(modId + ":block/" + objectId))), new Identifier(modId, objectId));
            RESOURCE_PACK.addModel(JModel.model("minecraft:block/cube_all").textures(new JTextures().var("all", modId + ":block/" + objectId)), new Identifier(modId, "block/" + objectId));
        }
    }
}
