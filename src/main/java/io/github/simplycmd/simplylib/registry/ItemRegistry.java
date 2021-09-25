package io.github.simplycmd.simplylib.registry;

import io.github.simplycmd.simplylib.Main;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static net.devtech.arrp.json.models.JModel.model;

public class ItemRegistry {
    public static HashMap<ID, Item> items = new HashMap<>();
    private static boolean initialRegistry = true;

    public static void register() {
        // Handle multiple mods using SimplyLib at once without registering twice
        if (!initialRegistry)
            items.clear();
        initialRegistry = false;

        RegisterModItemCallback.EVENT.invoker().register(items);

        for (Map.Entry<ID, Item> item : items.entrySet()) {
            Registry.register(Registry.ITEM, item.getKey().getIdentifier(), item.getValue());
            Main.RESOURCE_PACK.addModel(model().parent("minecraft:item/generated").textures(JModel.textures().layer0(item.getKey().getNamespace() + ":item/" + item.getKey().getId())), new Identifier(item.getKey().getNamespace(), "item/" + item.getKey().getId()));
        }
    }

    public static Item get(ID itemId) {
        if (items != null) return items.get(itemId);
        else return Items.AIR;
    }
}
