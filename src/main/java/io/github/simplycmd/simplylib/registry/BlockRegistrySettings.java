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

        try { this.blockstateType = blockstateType; }
        catch (NullPointerException n) { this.blockstateType = BlockstateType.NORMAL; }

        try { this.itemModelType = itemModelType; }
        catch (NullPointerException n) { this.itemModelType = ItemModelType.NORMAL; }

        try { this.lootType = lootType; }
        catch (NullPointerException n) { this.lootType = LootType.NORMAL; }
    }
}
