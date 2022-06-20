package com.simplycmd.featherlib.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JResult;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryHandler implements ModInitializer {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(new Identifier("featherlib_mods", "resources"));
    private static final HashMap<Identifier, JLang> LANG = new HashMap<>();
    private static final UUID BLOCK_ITEM_UUID = UUID.randomUUID();
    private static boolean DEBUG = false;

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("registry", Object.class).forEach(entrypoint -> {
            var registryClass = entrypoint.getEntrypoint().getClass();
            if (registryClass.isAnnotationPresent(com.simplycmd.featherlib.registry.Registry.class)) {
                final var annotation = registryClass.getAnnotation(com.simplycmd.featherlib.registry.Registry.class);
                final var modId = annotation.modId();
                final var devOnly = annotation.devOnly();

                // Set debug
                if (DEBUG != true && annotation.debug() == true) DEBUG = true;

                // Initialize registry
                if (!devOnly || FabricLoader.getInstance().isDevelopmentEnvironment()) {
                    for (var field : registryClass.getDeclaredFields()) {
                        initialize(modId, registryClass, field);
                    }
                }
                
            }
        });
        for (var lang : LANG.entrySet()) {
            RESOURCE_PACK.addLang(lang.getKey(), lang.getValue());
        }
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
        if (DEBUG) RESOURCE_PACK.dump();
    }

    private void initialize(String modId, Class<?> clazz, Field field) {
        try {
            // Send all declared fields to be registered
            final var name = field.getName().toLowerCase();
            var value = field.get(clazz);
            register(modId, name, value);

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && field.isAnnotationPresent(Lang.class)) {
                final var lang = (Lang)field.getAnnotation(Lang.class);
                final var id = new Identifier(modId, lang.lang());
                LANG.putIfAbsent(id, new JLang());
                if (value instanceof Item) {
                    LANG.replace(id, LANG.get(id).itemRespect((Item) value, lang.name()));
                }
                if (value instanceof Block) {
                    LANG.replace(id, LANG.get(id).blockRespect((Block) value, lang.name()));
                }
                if (value instanceof Torch) {
                    LANG.replace(id, LANG.get(id).blockRespect(((Torch) value).torchBlock, lang.name()));
                }
            }

            // BlockItem & Render annotation handler
            if (value instanceof Block || value instanceof Torch) {
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && field.isAnnotationPresent(Render.class)) {
                    final var render = (Render) field.getAnnotation(Render.class);
                    if (value instanceof Torch) {
                        BlockRenderLayerMap.INSTANCE.putBlock(((Torch) value).torchBlock, render.layer().getLayer());
                        BlockRenderLayerMap.INSTANCE.putBlock(((Torch) value).wallTorchBlock, render.layer().getLayer());
                    }
                    if (value instanceof Block) {
                        BlockRenderLayerMap.INSTANCE.putBlock((Block) value, render.layer().getLayer());
                    }
                }

                if (value instanceof Block) {
                    final var bi = (com.simplycmd.featherlib.registry.BlockItem) field.getAnnotation(com.simplycmd.featherlib.registry.BlockItem.class);
                    if (bi != null) register(modId, name, new BlockItem((Block) field.get(clazz), new FabricItemSettings()
                        .group(bi.group().getGroup())
                        .rarity(bi.rarity())
                        .maxDamage(bi.maxDamage())
                        .maxCount(bi.maxCount())
                    ));
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
            RESOURCE_PACK.addBlockState(JBlockStates.ofVariants(new JVariants()
                .addVariant("facing", "east", new JBlockModel(new Identifier(modId, "block/" + wallTorchName)))
                .addVariant("facing", "north", new JBlockModel(new Identifier(modId, "block/" + wallTorchName)).y(270))
                .addVariant("facing", "south", new JBlockModel(new Identifier(modId, "block/" + wallTorchName)).y(90))
                .addVariant("facing", "west", new JBlockModel(new Identifier(modId, "block/" + wallTorchName)).y(180))
            ), new Identifier(modId, wallTorchName));

            RESOURCE_PACK.addModel(new JModel("minecraft:block/template_torch").textures(new JTextures().var("torch", modId + ":block/" + torchName)), new Identifier(modId, "block/" + torchName));
            RESOURCE_PACK.addModel(new JModel("minecraft:block/template_torch_wall").textures(new JTextures().var("torch", modId + ":block/" + torchName)), new Identifier(modId, "block/" + wallTorchName));
            RESOURCE_PACK.addModel(new JModel("minecraft:item/generated").textures(new JTextures().layer0(modId + ":block/" + torchName)), new Identifier(modId, "item/" + torchName));

            RESOURCE_PACK.addRecipe(new Identifier(modId, objectId), new JShapedRecipe(
                new JResult(modId + ":" + objectId).count(4),
                new JPattern("X", "#", "S"),
                new JKeys()
                    .key("X", new JIngredient().item(Items.COAL).item(Items.CHARCOAL))
                    .key("#", new JIngredient().item(Items.STICK))
                    .key("S", torch.getResource())
            ));
            RESOURCE_PACK.addRecipe(new Identifier(modId, objectId + "_shorthand"), new JShapelessRecipe(new JResult(modId + ":" + objectId).count(4), List.of(new JIngredient().item(Items.TORCH), torch.getResource())));
        }

        // Item registration and data
        if (object instanceof Item) {
            final var id = new Identifier(modId, "item/" + objectId);
            Registry.register(Registry.ITEM, new Identifier(modId, objectId.replaceAll(BLOCK_ITEM_UUID.toString(), "")), (Item) object);
            if (object instanceof BlockItem) {
                RESOURCE_PACK.addModel(new JModel(modId + ":block/" + objectId), id);
            } else {
                RESOURCE_PACK.addModel(new JModel("minecraft:item/generated").textures(new JTextures().layer0(modId + ":item/" + objectId)), id);
            }
        }

        // Block registration and data
        if (object instanceof Block) {
            Registry.register(Registry.BLOCK, new Identifier(modId, objectId), (Block) object);
            RESOURCE_PACK.addBlockState(JBlockStates.ofVariants(JVariants.ofNoVariants(new JBlockModel(new Identifier(modId, "block/" + objectId)))), new Identifier(modId, objectId));
            RESOURCE_PACK.addModel(new JModel("minecraft:block/cube_all").textures(new JTextures().var("all", modId + ":block/" + objectId)), new Identifier(modId, "block/" + objectId));
        }
        
    }

    public static class Torch implements ItemConvertible {
        private final @Getter TorchBlock torchBlock;
        private final @Getter WallTorchBlock wallTorchBlock;
        private final @Getter WallStandingBlockItem item;
        private final @Getter JIngredient resource;

        public Torch(ParticleEffect particle, JIngredient resource) {
            this.torchBlock = new TorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance((state) -> 10).sounds(BlockSoundGroup.WOOD), particle);
            this.wallTorchBlock = new WallTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance((state) -> 10).sounds(BlockSoundGroup.WOOD), particle);
            this.item = new WallStandingBlockItem(torchBlock, wallTorchBlock, new FabricItemSettings().group(ItemGroup.DECORATIONS));
            this.resource = resource;
        }

        @Override
        public Item asItem() {
            return item;
        }
    }
}
