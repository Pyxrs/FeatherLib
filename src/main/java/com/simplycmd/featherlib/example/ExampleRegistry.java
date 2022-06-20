package com.simplycmd.featherlib.example;

import com.simplycmd.featherlib.registry.BlockItem;
import com.simplycmd.featherlib.registry.Lang;
import com.simplycmd.featherlib.registry.Registry;
import com.simplycmd.featherlib.registry.Render;
import com.simplycmd.featherlib.registry.BlockItem.ItemGroup;
import com.simplycmd.featherlib.registry.RegistryHandler.Torch;
import com.simplycmd.featherlib.registry.Render.RenderLayer;

import net.devtech.arrp.json.recipe.JIngredient;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;

/**
 * @see Registry
 */
@Registry(modId = "featherlib", devOnly = true)
public class ExampleRegistry {
    @Lang(lang = "en_us", name = "Example Block (Development Environment Only)")
    @BlockItem(maxCount = 23, group = ItemGroup.DECORATIONS)
    public static final Block EXAMPLE_BLOCK = new Block(FabricBlockSettings.of(Material.STONE));

    @Lang(lang = "en_us", name = "Example Torch (Development Environment Only)")
    @Render(layer = RenderLayer.CUTOUT)
    public static final Torch EXAMPLE_TORCH = new Torch(ParticleTypes.SOUL_FIRE_FLAME, new JIngredient().item(Items.APPLE));

    @Lang(lang = "en_us", name = "Example Item (Development Environment Only)")
    public static final Item EXAMPLE_ITEM = new Item(new FabricItemSettings().group(net.minecraft.item.ItemGroup.MATERIALS));
}