package io.github.simplycmd.simplylib.registry;

import lombok.Getter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
    final ID id;

    @Getter
    BlockstateType blockstateType;

    @Getter
    ItemModelType itemModelType;

    @Getter
    LootType lootType;

    public BlockRegistrySettings(ID id) {
        this.id = id;

        blockstateType = BlockstateType.NORMAL;

        this.itemModelType = ItemModelType.NORMAL;

        this.lootType = LootType.NORMAL;
    }

    public BlockRegistrySettings blockstateType(BlockstateType blockstateType) {
        this.blockstateType = blockstateType;
        return this;
    }

    public BlockRegistrySettings itemModelType(ItemModelType itemModelType) {
        this.itemModelType = itemModelType;
        return this;
    }

    public BlockRegistrySettings lootType(LootType lootType) {
        this.lootType = lootType;
        return this;
    }
}
