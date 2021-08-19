package io.github.simplycmd.simplylib.registry;

import io.github.simplycmd.simplylib.Main;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static net.devtech.arrp.json.models.JModel.model;

public class ItemRegistry {
    private static HashMap<String, Item> items = new HashMap<>();

    public static void register() {
        RegisterModItemCallback.EVENT.invoker().register(items);

        for (Map.Entry<String, Item> item : items.entrySet()) {
            Registry.register(Registry.ITEM, Main.ID(item.getKey()), item.getValue());
            Main.RESOURCE_PACK.addModel(model().parent("minecraft:item/generated").textures(JModel.textures().layer0(Main.MOD_ID + ":item/" + item.getKey())), Main.ID("item/" + item.getKey()));
        }
    }

    public static Item get(String itemId) {
        if (items != null) return items.get(itemId);
        else return Items.AIR;
    }
}
