package com.simplycmd.featherlib.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

/**
 * Simplified version of {@link net.minecraft.entity.Entity}
 */
public abstract class BaseEntity extends Entity {
    public BaseEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    public void tick() {
        move(MovementType.SELF, getVelocity());
        super.tick();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
