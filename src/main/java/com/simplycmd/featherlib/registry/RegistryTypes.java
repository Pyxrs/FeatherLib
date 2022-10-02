package com.simplycmd.featherlib.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.List;

import com.simplycmd.featherlib.Resources;
import com.simplycmd.featherlib.registry.RegistryHandler.Sound;
import com.simplycmd.featherlib.registry.RegistryHandler.Torch;
import com.simplycmd.featherlib.util.Tuple.Bi;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JResult;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public enum RegistryTypes {
    ITEM(RegistryType.builder(Item.class)
        .lang((object, lang, translation) -> lang.itemRespect(object.b, translation))
        .render((object, layermap, layer) -> layermap.putItem(object.b, layer))
        .build((object, register) -> {
            final var id = object.a;
            Registry.register(Registry.ITEM, id, object.b);
            if (object.b instanceof BlockItem)
                Resources.addResource((pack) -> pack.addModel(new JModel(id.getNamespace() + ":block/" + id.getPath()), new Identifier(id.getNamespace(), "item/" + id.getPath())));
            else
                Resources.addResource((pack) -> pack.addModel(new JModel("minecraft:item/generated").textures(new JTextures().layer0(id.getNamespace() + ":item/" + id.getPath())), new Identifier(id.getNamespace(), "item/" + id.getPath())));
        })),

    BLOCK(RegistryType.builder(Block.class)
        .lang((object, lang, translation) -> lang.blockRespect(object.b, translation))
        .render((object, layermap, layer) -> layermap.putBlock(object.b, layer))
        .build((object, register) -> {
            final var id = object.a;
            Registry.register(Registry.BLOCK, id, (Block) object.b);
            Resources.addResource((pack) -> pack.addBlockState(JBlockStates.ofVariants(JVariants.ofNoVariants(new JBlockModel(new Identifier(id.getNamespace(), "block/" + id.getPath())))), id));
            Resources.addResource((pack) -> pack.addModel(new JModel("minecraft:block/cube_all").textures(new JTextures().var("all", id.getNamespace() + ":block/" + id.getPath())), new Identifier(id.getNamespace(), "block/" + id.getPath())));
        })),
    
    TORCH(RegistryType.builder(Torch.class)
        .lang((object, lang, translation) -> lang.blockRespect(object.b.getTorchBlock(), translation))
        .render((object, layermap, layer) -> {
            layermap.putBlock(object.b.getTorchBlock(), layer);
            layermap.putBlock(object.b.getWallTorchBlock(), layer);
        })
        .build((object, register) -> {
            final var id = object.a;
            final var torch = object.b;
            final var torchName = new Identifier(id.getNamespace(), id.getPath().replaceAll("_torch", "") + "_torch");
            final var wallTorchName = new Identifier(id.getNamespace(), id.getPath().replaceAll("_torch", "") + "_wall_torch");
            final var torchItemName = new Identifier(id.getNamespace(), id.getPath().replaceAll("_torch", "") + "_torch");
            RegistryTypes.BLOCK.getType().getRegister().accept(new Bi<Identifier, Object>(torchName, torch.getTorchBlock()), register);
            RegistryTypes.BLOCK.getType().getRegister().accept(new Bi<Identifier, Object>(wallTorchName, torch.getWallTorchBlock()), register);
            RegistryTypes.ITEM.getType().getRegister().accept(new Bi<Identifier, Object>(torchItemName, torch.asItem()), register);

            // The following data is added AFTER the normal block and item data for torches, so the default templates are overridden.

            // BlockState
            Resources.addResource((pack) -> pack.addBlockState(JBlockStates.ofVariants(new JVariants()
                .addVariant("facing", "east", new JBlockModel(new Identifier(id.getNamespace(), "block/" + wallTorchName.getPath())))
                .addVariant("facing", "north", new JBlockModel(new Identifier(id.getNamespace(), "block/" + wallTorchName.getPath())).y(270))
                .addVariant("facing", "south", new JBlockModel(new Identifier(id.getNamespace(), "block/" + wallTorchName.getPath())).y(90))
                .addVariant("facing", "west", new JBlockModel(new Identifier(id.getNamespace(), "block/" + wallTorchName.getPath())).y(180))
            ), wallTorchName));

            // Models
            Resources.addResource((pack) -> pack.addModel(new JModel("minecraft:block/template_torch").textures(new JTextures().var("torch", id.getNamespace() + ":block/" + torchName.getPath())), new Identifier(id.getNamespace(), "block/" + torchName.getPath())));
            Resources.addResource((pack) -> pack.addModel(new JModel("minecraft:block/template_torch_wall").textures(new JTextures().var("torch", id.getNamespace() + ":block/" + torchName.getPath())), new Identifier(id.getNamespace(), "block/" + wallTorchName.getPath())));
            Resources.addResource((pack) -> pack.addModel(new JModel("minecraft:item/generated").textures(new JTextures().layer0(id.getNamespace() + ":block/" + torchName.getPath())), new Identifier(id.getNamespace(), "item/" + torchName.getPath())));

            // Recipes
            Resources.addResource((pack) -> pack.addRecipe(id, new JShapedRecipe(
                new JResult(id.getNamespace() + ":" + id.getPath()).count(4),
                new JPattern("X", "#", "S"),
                new JKeys()
                    .key("X", new JIngredient().item(Items.COAL).item(Items.CHARCOAL))
                    .key("#", new JIngredient().item(Items.STICK))
                    .key("S", torch.getResource())
            )));
            Resources.addResource((pack) -> pack.addRecipe(new Identifier(id.getNamespace(), id.getPath() + "_shorthand"), new JShapelessRecipe(new JResult(id.getNamespace() + ":" + id.getPath()).count(4), List.of(new JIngredient().item(Items.TORCH), torch.getResource()))));
        })),
    
    SOUND(RegistryType.builder(Sound.class)
        .lang((object, lang, translation) -> lang.sound(object.a, translation))
        .build((object, register) -> {
            object.b.name = object.a.getPath();
            RegistryHandler.SOUNDS.add(object.b);
            Registry.register(Registry.SOUND_EVENT, object.a, new SoundEvent(object.a));
        }));

    private RegistryType<?> type;

    private RegistryTypes(RegistryType<?> type) {
        this.type = type;
    }

    public RegistryType<?> getType() {
        return type;
    }
}
