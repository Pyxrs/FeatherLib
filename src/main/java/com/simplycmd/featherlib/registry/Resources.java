package com.simplycmd.featherlib.registry;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JPosition;
import net.minecraft.util.Identifier;

import static net.devtech.arrp.json.loot.JLootTable.*;

import com.simplycmd.featherlib.FeatherLib;
import com.simplycmd.featherlib.registry.Resources;

public class Resources {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(FeatherLib.MOD_ID + ":resource_pack");

    public static final void register() {
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
    }

    public static JLootTable registerDefaultBlockLootTable(Identifier blockId) {
        final JLootTable loot = loot("minecraft:block")
            .pool(pool()
                .rolls(1)
                .entry(entry()
                    .type("minecraft:item")
                    .name(blockId.getNamespace() + ":" + blockId.getPath()))
                .condition(predicate("minecraft:survives_explosion")));
        Resources.RESOURCE_PACK.addLootTable(new Identifier(blockId.getNamespace(), "blocks/" + blockId.getPath()), loot);
        return loot;
    }

    public static JState registerDefaultBlockstate(Identifier blockId) {
        final JState state = JState.state(JState.variant(JState.model(blockId.getNamespace() + ":block/" + blockId.getPath())));
        Resources.RESOURCE_PACK.addBlockState(state, blockId);
        return state;
    }

    public static JModel registerTextureItemModel(Identifier blockId) {
        final JModel model = JModel.model().parent("minecraft:item/generated").textures(JModel.textures().layer0(blockId.getNamespace() + ":item/" + blockId.getPath()));
        Resources.RESOURCE_PACK.addModel(model, new Identifier(blockId.getNamespace(), "item/" + blockId.getPath()));
        return model;
    }

    public static JModel registerBlockItemModel(Identifier blockId) {
        final JModel model = JModel.model().parent(blockId.getNamespace() + ":block/" + blockId.getPath()).display(JModel.display()
            .setGui(new JPosition().rotation(30, 45, 0).scale(0.625f, 0.625f, 0.625f))
            .setGround(new JPosition().translation(0, 3, 0).scale(0.25f, 0.25f, 0.25f))
            .setHead(new JPosition().rotation(0, 180, 0).scale(1, 1, 1))
            .setFixed(new JPosition().rotation(0, 180, 0).scale(0.5f, 0.5f, 0.5f))
            .setThirdperson_righthand(new JPosition().rotation(75, 315, 0).translation(0, 2.5f, 0).scale(0.375f, 0.375f, 0.375f))
            .setFirstperson_righthand(new JPosition().rotation(0, 315, 0).scale(0.4f, 0.4f, 0.4f))
        );
        Resources.RESOURCE_PACK.addModel(model, new Identifier(blockId.getNamespace(), "item/" + blockId.getPath()));
        return model;
    }
}
