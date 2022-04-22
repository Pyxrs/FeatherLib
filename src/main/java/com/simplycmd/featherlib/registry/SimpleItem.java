package com.simplycmd.featherlib.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

import java.util.Optional;
import java.util.function.BiConsumer;

import lombok.Getter;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SimpleItem implements ItemConvertible {
    @Getter
    private final Identifier id;
    private final Item item;

    protected final Optional<BiConsumer<Item, ItemModelGenerator>> resources;
    
    public SimpleItem(Identifier id, Item item) {
        this.id = id;
        this.item = item;
        this.resources = Optional.empty();
    }
    public SimpleItem(Identifier id, Item item, BiConsumer<Item, ItemModelGenerator> resources) {
        this.id = id;
        this.item = item;
        this.resources = Optional.of(resources);
    }

    protected void register() {
        Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), id.getPath()), item);
    }

    /**
     * @deprecated Use {@link #asItem()} instead.
     */
    @Deprecated
    public Item getItem() {
        return item;
    }

    @Override
    public Item asItem() {
        return item;
    }
}