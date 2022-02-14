package io.github.simplycmd.featherlib.util;

import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JPosition;

import static net.devtech.arrp.json.loot.JLootTable.*;

import io.github.simplycmd.featherlib.registry.Identifier;

public class ARRP {
    public static JLootTable defaultBlockLootTable(Identifier blockId) {
        return loot("minecraft:block")
            .pool(pool()
                .rolls(1)
                .entry(entry()
                    .type("minecraft:item")
                    .name(blockId.getNamespace() + ":" + blockId.getPath()))
                .condition(predicate("minecraft:survives_explosion")));
    }

    public static JState defaultBlockstate(Identifier blockId) {
        return JState.state(JState.variant(JState.model(blockId.getNamespace() + ":block/" + blockId.getPath())));
    }

    public static JModel textureItemModel(Identifier blockId) {
        return JModel.model().parent("minecraft:item/generated").textures(JModel.textures().layer0(blockId.getNamespace() + ":item/" + blockId.getPath()));
    }

    public static JModel blockItemModel(Identifier blockId) {
        return JModel.model().parent(blockId.getNamespace() + ":block/" + blockId.getPath()).display(JModel.display()
            .setGui(new JPosition().rotation(30, 45, 0).scale(0.625f, 0.625f, 0.625f))
            .setGround(new JPosition().translation(0, 3, 0).scale(0.25f, 0.25f, 0.25f))
            .setHead(new JPosition().rotation(0, 180, 0).scale(1, 1, 1))
            .setFixed(new JPosition().rotation(0, 180, 0).scale(0.5f, 0.5f, 0.5f))
            .setThirdperson_righthand(new JPosition().rotation(75, 315, 0).translation(0, 2.5f, 0).scale(0.375f, 0.375f, 0.375f))
            .setFirstperson_righthand(new JPosition().rotation(0, 315, 0).scale(0.4f, 0.4f, 0.4f))
        );
    }
}
