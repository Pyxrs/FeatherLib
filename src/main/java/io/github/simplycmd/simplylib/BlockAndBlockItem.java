package io.github.simplycmd.simplylib;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BlockAndBlockItem {
    @Getter
    @Setter
    Block block;

    @Getter
    @Setter
    BlockItem blockItem;

    public BlockAndBlockItem(Block block, BlockItem blockItem) {
        this.block = block;
        this.blockItem = blockItem;
    }
}
