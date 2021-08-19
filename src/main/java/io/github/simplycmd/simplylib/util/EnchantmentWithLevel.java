package io.github.simplycmd.simplylib.util;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.enchantment.Enchantment;

public class EnchantmentWithLevel {
    @Getter
    @Setter
    Enchantment enchantment;

    @Getter
    @Setter
    int level;

    public EnchantmentWithLevel(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }
}
