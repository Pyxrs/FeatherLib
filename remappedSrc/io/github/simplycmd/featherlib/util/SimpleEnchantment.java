package io.github.simplycmd.featherlib.util;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.enchantment.Enchantment;

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
