package io.github.simplycmd.featherlib.util;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import io.github.simplycmd.featherlib.registry.ID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SimpleItem extends Item {
    private final Optional<List<Text>> tooltip;

    public SimpleItem(ID itemId, FabricItemSettings settings) {
        super(settings);
        this.tooltip = Optional.empty();
        Registry.register(Registry.ITEM, new Identifier(itemId.getNamespace(), itemId.getId()), new Item(settings));
    }

    public SimpleItem(ID itemId, FabricItemSettings settings, List<Text> tooltip) {
        super(settings);
        this.tooltip = Optional.of(tooltip);
        Registry.register(Registry.ITEM, new Identifier(itemId.getNamespace(), itemId.getId()), new Item(settings));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.tooltip.isPresent()) {
            for (Text line : this.tooltip.get()) {
                tooltip.add(line);
            }
        }
    }
}