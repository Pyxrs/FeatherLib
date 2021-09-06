package io.github.simplycmd.simplylib.registry;

import io.github.simplycmd.simplylib.Main;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JPosition;

import static net.devtech.arrp.json.loot.JLootTable.*;

public class ARRPUtil {
    public static JLootTable lootTable(String blockId, BlockRegistrySettings.LootType type) {
        return switch (type) {
            case NORMAL -> loot("minecraft:block")
                    .pool(pool()
                            .rolls(1)
                            .entry(entry()
                                    .type("minecraft:item")
                                    .name(Main.MOD_ID + ":" + blockId))
                            .condition(predicate("minecraft:survives_explosion")));
            case NONE -> loot("minecraft:block");
        };
    }

    public static JState blockstate(String blockId, BlockRegistrySettings.BlockstateType type) {
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

    public static JModel model(String blockId, BlockRegistrySettings.ItemModelType type) {
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
