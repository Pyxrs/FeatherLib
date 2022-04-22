package com.simplycmd.featherlib.registry;

import com.simplycmd.featherlib.FeatherLib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.Identifier;

public class SimpleRegistry implements DataGeneratorEntrypoint, ModInitializer {
    private final SimpleBlock[] blocks;
    private final SimpleItem[] items;

    protected SimpleRegistry(SimpleBlock[] blocks, SimpleItem[] items) {
        this.blocks = blocks;
        this.items = items;
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(new FabricModelProvider(fabricDataGenerator) {
            @Override
            public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
                for (var block : blocks) block.resources.ifPresent((resources) -> resources.accept(block.getBlock(), blockStateModelGenerator));
            }

            @Override
            public void generateItemModels(ItemModelGenerator itemModelGenerator) {
                for (var item : items) item.resources.ifPresent((resources) -> resources.accept(item.asItem(), itemModelGenerator));
            }
        });
    }

    @Override
    public void onInitialize() {
        for (var block : blocks) block.register();
        for (var item : items) item.register();
    }

    /**
     * Shadow this method with your own!
     * Example: {@code public static Identifier ID(String id) {
     *      return new Identifier(YourMainClass.MOD_ID, id);
     * }}
     */
    public static Identifier ID(String id) {
        return new Identifier(FeatherLib.MOD_ID, "you_must_shadow_simpleregistry_id_with_your_own_" + id);
    }
}
