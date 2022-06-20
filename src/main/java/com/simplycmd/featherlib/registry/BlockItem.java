package com.simplycmd.featherlib.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.Getter;
import net.minecraft.util.Rarity;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BlockItem {
    ItemGroup group() default ItemGroup.SEARCH;
    Rarity rarity() default Rarity.COMMON;
    // TODO: recipeRemainder
    int maxDamage() default 0;
    int maxCount() default 64;
    // TODO: foodComponent
    // TODO: fireproof

    enum ItemGroup {
        BUILDING_BLOCKS(net.minecraft.item.ItemGroup.BUILDING_BLOCKS),
        DECORATIONS(net.minecraft.item.ItemGroup.DECORATIONS),
        REDSTONE(net.minecraft.item.ItemGroup.REDSTONE),
        TRANSPORTATION(net.minecraft.item.ItemGroup.TRANSPORTATION),
        MISC(net.minecraft.item.ItemGroup.MISC),
        SEARCH(net.minecraft.item.ItemGroup.SEARCH),
        FOOD(net.minecraft.item.ItemGroup.FOOD),
        TOOLS(net.minecraft.item.ItemGroup.TOOLS),
        COMBAT(net.minecraft.item.ItemGroup.COMBAT),
        BREWING(net.minecraft.item.ItemGroup.BREWING),
        MATERIALS(net.minecraft.item.ItemGroup.MATERIALS),
        HOTBAR(net.minecraft.item.ItemGroup.HOTBAR),
        INVENTORY(net.minecraft.item.ItemGroup.INVENTORY);

        @Getter
        private final net.minecraft.item.ItemGroup group;

        ItemGroup(net.minecraft.item.ItemGroup group) {
            this.group = group;
        }
    }
}
