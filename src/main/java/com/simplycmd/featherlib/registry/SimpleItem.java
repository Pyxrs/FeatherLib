package com.simplycmd.featherlib.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SimpleItem {
    private final Item item;
    private final Identifier id;

    public SimpleItem(Identifier id, Item item) {
        this.id = id;
        this.item = item;
        Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), id.getPath()), item);
    }

    public Identifier getId() {
        return id;
    }
    public Item getItem() {
        return item;
    }
}