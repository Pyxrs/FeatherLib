package io.github.simplycmd.simplylib.registry;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public class BlockRegistrySettings {
    public enum BlockstateType {
        NORMAL,
        RANDOM_X,
        RANDOM_Y,
        RANDOM,
    }
    public enum ItemModelType {
        NORMAL,
        TEXTURE
    }
    public enum LootType {
        NORMAL,
        NONE
    }

    @Getter
    String id;

    @Getter
    BlockstateType blockstateType;

    @Getter
    ItemModelType itemModelType;

    @Getter
    LootType lootType;

    public BlockRegistrySettings(String id, @Nullable BlockstateType blockstateType, @Nullable ItemModelType itemModelType, @Nullable LootType lootType) {
        this.id = id;

        if (blockstateType != null) this.blockstateType = blockstateType;
        else this.blockstateType = BlockstateType.NORMAL;

        if (itemModelType != null) this.itemModelType = itemModelType;
        else this.itemModelType = ItemModelType.NORMAL;

        if (lootType != null) this.lootType = lootType;
        else this.lootType = LootType.NORMAL;
    }
}
