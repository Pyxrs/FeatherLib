/*package io.github.simplycmd.featherlib.registry;

import java.util.Set;

import com.mojang.datafixers.types.Type;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public class SimpleBlockEntityType<T extends BlockEntity> extends BlockEntityType<T> {
    public SimpleBlockEntityType(FabricBlockEntityTypeBuilder<? extends T> factory, Set<Block> blocks, Type<?> type) {
        super(FabricBlockEntityTypeBuilder.create(DemoBlockEntity::new, DEMO_BLOCK).build(null), blocks, type);
    }
}
*/