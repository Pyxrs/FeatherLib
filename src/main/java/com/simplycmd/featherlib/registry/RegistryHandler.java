package com.simplycmd.featherlib.registry;

import java.lang.reflect.Field;
import java.util.UUID;

import com.simplycmd.featherlib.FeatherLib;
import com.simplycmd.featherlib.util.CustomTorchBlock;
import com.simplycmd.featherlib.util.CustomWallTorchBlock;

import lombok.Getter;

import org.atteo.classindex.ClassIndex;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryHandler implements ModInitializer {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(new Identifier(FeatherLib.MOD_ID + "_mods", "resources"));
    private static final UUID BLOCK_ITEM_UUID = UUID.randomUUID();

    @Override
    public void onInitialize() {
        for (var clazz : ClassIndex.getAnnotated(com.simplycmd.featherlib.registry.Registry.class)) {
            final var modId = clazz.getAnnotation(com.simplycmd.featherlib.registry.Registry.class).modId();
            for (var field : clazz.getDeclaredFields()) {
                initialize(modId, clazz, field);
            }
        }
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
        RESOURCE_PACK.dump();
    }

    private void initialize(String modId, Class<?> clazz, Field field) {
        try {
            // Send all declared fields to be registered
            final var name = field.getName().toLowerCase();
            var value = field.get(clazz);
            register(modId, name, value);

            // BlockItem & Render annotation handler
            if (value instanceof Block || value instanceof Torch) {
                final var render = (com.simplycmd.featherlib.registry.Render)field.getAnnotation(com.simplycmd.featherlib.registry.Render.class);

                if (value instanceof Block) {
                    final var bi = (com.simplycmd.featherlib.registry.BlockItem)field.getAnnotation(com.simplycmd.featherlib.registry.BlockItem.class);
                    if (bi != null) register(modId, name, new BlockItem((Block)field.get(clazz), new FabricItemSettings()
                        .group(bi.group().getGroup())
                        .rarity(bi.rarity())
                        .maxDamage(bi.maxDamage())
                        .maxCount(bi.maxCount())
                    ));
                    if (render != null) BlockRenderLayerMap.INSTANCE.putBlock((Block) value, render.layer().getLayer());
                }

                if (value instanceof Torch && render != null) {
                    BlockRenderLayerMap.INSTANCE.putBlock(((Torch) value).torchBlock, render.layer().getLayer());
                    BlockRenderLayerMap.INSTANCE.putBlock(((Torch) value).wallTorchBlock, render.layer().getLayer());
                }
                
            }
            
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void register(String modId, String objectId, Object object) {

        // Torch registration and data using recursion
        if (object instanceof Torch) {
            var torch = (Torch) object;
            var torchName = objectId.replaceAll("_torch", "") + "_torch";
            var wallTorchName = objectId.replaceAll("_torch", "") + "_wall_torch";
            register(modId, torchName, torch.getTorchBlock());
            register(modId, wallTorchName, torch.getWallTorchBlock());
            register(modId, objectId.replaceAll("_torch", "") + "_torch", torch.getItem());

            // The following data is added AFTER the normal block and item data for torches, so it is overridden.
            RESOURCE_PACK.addBlockState(JState.state(new JVariant()
                .put("facing=east", JState.model(modId + ":block/" + wallTorchName))
                .put("facing=north", JState.model(modId + ":block/" + wallTorchName).y(270))
                .put("facing=south", JState.model(modId + ":block/" + wallTorchName).y(90))
                .put("facing=west", JState.model(modId + ":block/" + wallTorchName).y(180))
            ), new Identifier(modId, wallTorchName));

            RESOURCE_PACK.addModel(JModel.model("minecraft:block/template_torch").textures(new JTextures().var("torch", modId + ":block/" + torchName)), new Identifier(modId, "block/" + torchName));
            RESOURCE_PACK.addModel(JModel.model("minecraft:block/template_torch_wall").textures(new JTextures().var("torch", modId + ":block/" + torchName)), new Identifier(modId, "block/" + wallTorchName));
            RESOURCE_PACK.addModel(JModel.model("minecraft:item/generated").textures(new JTextures().layer0(modId + ":block/" + torchName)), new Identifier(modId, "item/" + torchName));

            RESOURCE_PACK.addRecipe(new Identifier(modId, objectId), JRecipe.shaped(
                JPattern.pattern("X", "#", "S"),
                JKeys.keys()
                    .key("X", JIngredient.ingredient().item(Items.COAL).item(Items.CHARCOAL))
                    .key("#", JIngredient.ingredient().item(Items.STICK))
                    .key("S", torch.getResource()),
                JResult.itemStack(torch.getItem(), 4)
            ));
            RESOURCE_PACK.addRecipe(new Identifier(modId, objectId + "_shorthand"), JRecipe.shapeless(JIngredients.ingredients().add(JIngredient.ingredient().item(Items.TORCH)).add(torch.getResource()), JResult.itemStack(torch.getItem(), 4)));
        }

        // Item registration and data
        if (object instanceof Item) {
            final var id = new Identifier(modId, "item/" + objectId);
            Registry.register(Registry.ITEM, new Identifier(modId, objectId.replaceAll(BLOCK_ITEM_UUID.toString(), "")), (Item) object);
            if (object instanceof BlockItem) {
                RESOURCE_PACK.addModel(JModel.model(modId + ":block/" + objectId), id);
            } else {
                RESOURCE_PACK.addModel(JModel.model("minecraft:item/generated").textures(new JTextures().layer0(modId + ":item/" + objectId)), id);
            }
        }

        // Block registration and data
        if (object instanceof Block) {
            Registry.register(Registry.BLOCK, new Identifier(modId, objectId), (Block) object);
            RESOURCE_PACK.addBlockState(JState.state(new JVariant().put("", JState.model(modId + ":block/" + objectId))), new Identifier(modId, objectId));
            RESOURCE_PACK.addModel(JModel.model("minecraft:block/cube_all").textures(new JTextures().var("all", modId + ":block/" + objectId)), new Identifier(modId, "block/" + objectId));
        }
        
    }

    public static class Torch implements ItemConvertible {
        private final @Getter CustomTorchBlock torchBlock;
        private final @Getter CustomWallTorchBlock wallTorchBlock;
        private final @Getter WallStandingBlockItem item;
        private final @Getter JIngredient resource;

        public Torch(ParticleEffect particle, JIngredient resource) {
            this.torchBlock = new CustomTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance((state) -> 10).sounds(BlockSoundGroup.WOOD), particle);
            this.wallTorchBlock = new CustomWallTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance((state) -> 10).sounds(BlockSoundGroup.WOOD), particle);
            this.item = new WallStandingBlockItem(torchBlock, wallTorchBlock, new FabricItemSettings().group(ItemGroup.DECORATIONS));
            this.resource = resource;
        }

        @Override
        public Item asItem() {
            return item;
        }
    }
}
