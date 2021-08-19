package io.github.simplycmd.simplylib.registry;

import lombok.Getter;

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

    public BlockRegistrySettings(String id, BlockstateType blockstateType, ItemModelType itemModelType, LootType lootType) {
        this.id = id;
        this.blockstateType = blockstateType;
        this.itemModelType = itemModelType;
        this.lootType = lootType;
    }

    public BlockRegistrySettings(String id, BlockstateType blockstateType, ItemModelType itemModelType) {
        this.id = id;
        this.blockstateType = blockstateType;
        this.itemModelType = itemModelType;
        this.lootType = LootType.NORMAL;
    }

    public BlockRegistrySettings(String id, BlockstateType blockstateType) {
        this.id = id;
        this.blockstateType = blockstateType;
        this.itemModelType = ItemModelType.NORMAL;
        this.lootType = LootType.NORMAL;
    }

    public BlockRegistrySettings(String id, LootType lootType) {
        this.id = id;
        this.blockstateType = BlockstateType.NORMAL;
        this.itemModelType = ItemModelType.NORMAL;
        this.lootType = lootType;
    }

    public BlockRegistrySettings(String id) {
        this.id = id;
        this.blockstateType = BlockstateType.NORMAL;
        this.itemModelType = ItemModelType.NORMAL;
        this.lootType = LootType.NORMAL;
    }
}
