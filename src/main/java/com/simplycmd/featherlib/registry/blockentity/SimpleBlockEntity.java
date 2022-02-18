package com.simplycmd.featherlib.registry.blockentity;
/*package io.github.simplycmd.featherlib.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import lombok.Getter;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class SimpleBlockEntity extends BlockEntity {
    @Getter
    private final Block block;

    public <T extends BlockEntity> SimpleBlockEntity(Identifier id, FabricBlockEntityTypeBuilder.Factory<? extends T> typeBuilder, BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        block = new Block(id, FabricBlockSettings.copyOf(Blocks.STONE), BlockRenderType.INVISIBLE, this);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, id.getIdentifierString() + "_block_entity", FabricBlockEntityTypeBuilder.create(typeBuilder, block).build(null));
    }

    public <T extends BlockEntity> SimpleBlockEntity(Identifier id, Block block, FabricBlockEntityTypeBuilder.Factory<? extends T> typeBuilder, BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.block = block;
        Registry.register(Registry.BLOCK_ENTITY_TYPE, id.getIdentifierString() + "_block_entity", FabricBlockEntityTypeBuilder.create(typeBuilder, block).build(null));
    }

    public static class Block extends SimpleBlock implements BlockEntityProvider {
        private final BlockEntity blockEntity;
        private final BlockRenderType renderType;

        public Block(Identifier blockId, FabricBlockSettings settings, BlockRenderType renderType, BlockEntity blockEntity) {
            super(blockId, settings);
            this.blockEntity = blockEntity;
            this.renderType = renderType;
        }

        @Override
        public BlockRenderType getRenderType(BlockState state) {
            return renderType;
        }
    
        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return blockEntity;
        }
    }
}*/
