package io.github.simplycmd.simplylib.registry;

import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import org.jetbrains.annotations.Nullable;

public class SimplyLibBlockItem {
    @Getter
    private final ID id;

    @Getter
    private final FabricItemSettings settings;

    @Getter
    @Setter
    private BlockItem item;

    public SimplyLibBlockItem(ID id, FabricItemSettings settings) {
        this.id = id;
        this.settings = settings;
    }
}
