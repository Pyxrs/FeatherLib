package io.github.simplycmd.simplylib.registry.example;

import io.github.simplycmd.simplylib.Main;
import io.github.simplycmd.simplylib.registry.ID;
import io.github.simplycmd.simplylib.registry.ItemRegistry;
import io.github.simplycmd.simplylib.registry.RegisterModItemCallback;
import io.github.simplycmd.simplylib.scheduler.example.SchedulerExampleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ExampleItemRegistry {
    // Method is called in Main (go there if you're confused)
    public static void register() {
        RegisterModItemCallback.EVENT.register((items) -> {
            items.put(ID("simplylib_item"), new SchedulerExampleItem(new FabricItemSettings().group(ItemGroup.MISC)));
        });
        ItemRegistry.register();
    }

    public static Item get(String itemId) {
        return ItemRegistry.get(ID(itemId));
    }

    private static ID ID(String id) {
        // Your mod ID here
        return new ID(Main.MOD_ID, id);
    }
}
