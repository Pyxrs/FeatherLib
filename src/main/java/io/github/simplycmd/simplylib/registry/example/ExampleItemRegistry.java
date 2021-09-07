package io.github.simplycmd.simplylib.registry.example;

import io.github.simplycmd.simplylib.Main;
import io.github.simplycmd.simplylib.registry.ID;
import io.github.simplycmd.simplylib.registry.RegisterModItemCallback;
import io.github.simplycmd.simplylib.scheduler.example.SchedulerExampleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class ExampleItemRegistry {
    public static void register() {
        RegisterModItemCallback.EVENT.register((items) -> {
            items.put(ID("simplylib_item"), new SchedulerExampleItem(new FabricItemSettings().group(ItemGroup.MISC)));
        });
    }

    private static ID ID(String path) {
        return new ID(Main.MOD_ID, path);
    }
}
