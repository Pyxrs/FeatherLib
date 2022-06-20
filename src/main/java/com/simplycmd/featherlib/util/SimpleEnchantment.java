package com.simplycmd.featherlib.util;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.enchantment.Enchantment;

/**
 * Contains an {@link net.minecraft.enchantment.Enchantment} and its level, useful when working with enchantments
 */
public class SimpleEnchantment {
    @Getter
    @Setter
    Enchantment enchantment;

    @Getter
    @Setter
    int level;

    public SimpleEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }
}
