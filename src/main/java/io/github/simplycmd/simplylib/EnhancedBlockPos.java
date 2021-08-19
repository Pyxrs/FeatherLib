package io.github.simplycmd.simplylib;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class EnhancedBlockPos extends BlockPos {

    @Getter
    @Setter
    BlockState blockState;

    @Getter
    @Setter
    World world;

    public EnhancedBlockPos(World world, int i, int j, int k, BlockState blockState) {
        super(i, j, k);
        this.blockState = blockState;
        this.world = world;
    }

    public EnhancedBlockPos(World world, double d, double e, double f, BlockState blockState) {
        super(d, e, f);
        this.blockState = blockState;
        this.world = world;
    }

    public EnhancedBlockPos(World world, Vec3d pos, BlockState blockState) {
        this(world, pos.x, pos.y, pos.z, blockState);
    }

    public EnhancedBlockPos(World world, Position pos, BlockState blockState) {
        this(world, pos.getX(), pos.getY(), pos.getZ(), blockState);
    }

    public EnhancedBlockPos(World world, Vec3i pos, BlockState blockState) {
        this(world, pos.getX(), pos.getY(), pos.getZ(), blockState);
    }
}
