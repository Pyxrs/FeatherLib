package io.github.simplycmd.featherlib.util;

import io.github.simplycmd.featherlib.FeatherLib;
import io.github.simplycmd.featherlib.registry.ID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SimpleBlock extends Block {
    private final ID blockId;

    public SimpleBlock(ID blockId, FabricBlockSettings settings) {
        super(settings);
        this.blockId = blockId;
        Registry.register(Registry.BLOCK, new Identifier(blockId.getNamespace(), blockId.getId()), new Block(settings));
    }
    
    public SimpleBlock(ID blockId, FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super(blockSettings);
        this.blockId = blockId;
        final Block block = new Block(settings);
        final Identifier identifier = new Identifier(blockId.getNamespace(), blockId.getId());
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, itemSettings));
    }

    public ID getId() {
        return blockId;
    }

    public SimpleBlock defaultBlockstate() {
        FeatherLib.RESOURCE_PACK.addBlockState(ARRP.defaultBlockstate(blockId), this.getId().getIdentifier());
        return this;
    }

    public SimpleBlock textureItemModel() {
        FeatherLib.RESOURCE_PACK.addModel(ARRP.textureItemModel(blockId), new Identifier(this.getId().getNamespace(), "item/" + this.getId().getId()));
        return this;
    }

    public SimpleBlock blockItemModel() {
        FeatherLib.RESOURCE_PACK.addModel(ARRP.blockItemModel(blockId), new Identifier(this.getId().getNamespace(), "item/" + this.getId().getId()));
        return this;
    }

    public SimpleBlock defaultLootTable() {
        FeatherLib.RESOURCE_PACK.addLootTable(new Identifier(this.getId().getNamespace(), "blocks/" + this.getId().getId()), ARRP.defaultBlockLootTable(this.getId()));
        return this;
    }
}
