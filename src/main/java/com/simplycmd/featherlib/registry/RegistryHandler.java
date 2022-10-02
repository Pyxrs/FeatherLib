package com.simplycmd.featherlib.registry;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.simplycmd.featherlib.util.Tuple.Bi;

import lombok.Getter;
import net.devtech.arrp.api.JsonSerializable;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.recipe.JIngredient;
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
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class RegistryHandler implements ModInitializer {
    /**
     * @deprecated use {@link com.simplycmd.featherlib.Resources.addResource} instead
     */
    @Deprecated
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(new Identifier("featherlib_mods", "resources"));
    public static final ArrayList<Sound> SOUNDS = new ArrayList<Sound>();
    private static final HashMap<Identifier, JLang> LANG = new HashMap<>();

    private static boolean debug = false;

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("registry", Object.class).forEach(entrypoint -> {
            var registryClass = entrypoint.getEntrypoint().getClass();
            if (registryClass.isAnnotationPresent(com.simplycmd.featherlib.registry.Registry.class)) {
                final var annotation = registryClass.getAnnotation(com.simplycmd.featherlib.registry.Registry.class);
                final var modId = annotation.modId();
                final var devOnly = annotation.devOnly();

                // Set debug
                if (FabricLoader.getInstance().isDevelopmentEnvironment() && debug != true && annotation.debug() == true) debug = true;

                // Initialize registry
                if (!devOnly || FabricLoader.getInstance().isDevelopmentEnvironment()) {
                    for (var field : registryClass.getDeclaredFields()) {
                        initialize(modId, registryClass, field);
                    }
                }
                
                // Generate sounds file
                var sounds = new StringBuilder();
                sounds.append('{');
                SOUNDS.forEach((sound) -> {
                    sounds.append(", \n\"" + sound.name + "\": { \"subtitle\": \"subtitles." + modId + "." + sound.name + "\", \"sounds\": [ \"" + modId + ":" + sound.name + "\" ]}");
                });
                sounds.replace(1, 2, ""); // replace initial comma
                sounds.append('}');
                RESOURCE_PACK.addAsset(new Identifier(modId, "sounds.json"), sounds.toString().getBytes());
            }
        });
        for (var lang : LANG.entrySet()) {
            RESOURCE_PACK.addLang(lang.getKey(), lang.getValue());
        }
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
        if (debug) RESOURCE_PACK.dump();
    }

    private void initialize(String modId, Class<?> registry, Field field) {
        try {
            // Send all declared fields to be registered
            final var id = new Identifier(modId, field.getName().toLowerCase());
            var object = field.get(registry);
            Arrays.asList(RegistryTypes.values()).forEach((type) -> {
                final var register = type.getType().getRegister();
                if (object.getClass() == type.getType().getGeneric()) {
                    register.accept(new Bi<>(id, object), this);
                }
            });

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && field.isAnnotationPresent(Lang.class)) {
                final var langAnnotation = (Lang)field.getAnnotation(Lang.class);
                final var langId = new Identifier(modId, langAnnotation.lang());
                LANG.putIfAbsent(langId, new JLang());
                Arrays.asList(RegistryTypes.values()).forEach((type) -> {
                    type.getType().getLang().ifPresent((lang) -> {
                        if (object.getClass() == type.getType().getGeneric())
                            lang.accept(new Bi<>(id, object), LANG.get(langId), langAnnotation.name());
                    });
                });
            }

            // BlockItem & Render annotation handler
            if (object instanceof Block || object instanceof Torch) {
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && field.isAnnotationPresent(Render.class)) {
                    final var renderAnnotation = (Render) field.getAnnotation(Render.class);
                    Arrays.asList(RegistryTypes.values()).forEach((type) -> {
                        type.getType().getRender().ifPresent((render) -> {
                            if (object.getClass() == type.getType().getGeneric())
                                render.accept(new Bi<>(id, object), BlockRenderLayerMap.INSTANCE, renderAnnotation.layer().getLayer());
                        });
                    });
                }

                if (object instanceof Block) {
                    final var bi = (com.simplycmd.featherlib.registry.BlockItem) field.getAnnotation(com.simplycmd.featherlib.registry.BlockItem.class);
                    if (bi != null) RegistryTypes.ITEM.getType().getRegister().accept(new Bi<>(id, new BlockItem((Block) field.get(registry), new FabricItemSettings()
                        .group(bi.group().getGroup())
                        .rarity(bi.rarity())
                        .maxDamage(bi.maxDamage())
                        .maxCount(bi.maxCount())
                    )), this);
                }
            }
            
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static class Torch implements ItemConvertible {
        private final @Getter TorchBlock torchBlock;
        private final @Getter WallTorchBlock wallTorchBlock;
        @Deprecated
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
    public static class Sound {
        // This is handled later in the RegistryType
        String name;

        public Sound() {
        }
    }
}
