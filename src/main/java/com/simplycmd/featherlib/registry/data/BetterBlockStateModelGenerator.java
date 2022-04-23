package com.simplycmd.featherlib.registry.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gson.JsonElement;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.SimpleModelSupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class BetterBlockStateModelGenerator extends BlockStateModelGenerator {
    public BetterBlockStateModelGenerator(Consumer<BlockStateSupplier> blockStateCollector,
            BiConsumer<Identifier, Supplier<JsonElement>> modelCollector,
            Consumer<Item> simpleItemModelExemptionCollector) {
        super(blockStateCollector, modelCollector, simpleItemModelExemptionCollector);
    }

    public void registerParentedItemModel(Block block) {
        this.modelCollector.accept(ModelIds.getItemModelId(block.asItem()), new SimpleModelSupplier(ModelIds.getBlockModelId(block)));
    }

    public final void registerParentedItemModel(Item item) {
        this.modelCollector.accept(ModelIds.getItemModelId(item), new SimpleModelSupplier(ModelIds.getItemModelId(item)));
    }
}