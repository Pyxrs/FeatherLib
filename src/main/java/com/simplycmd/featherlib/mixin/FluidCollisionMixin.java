package com.simplycmd.featherlib.mixin;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.simplycmd.featherlib.events.FluidCollisionCallback;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(Entity.class)
public class FluidCollisionMixin {
    /**
	 * Adjusts an entity's movement to account for fluid walking. This ensures that the player never actually touches
	 * any of the fluids they can walk on.
	 *
	 * @param originalDisplacement the entity's proposed displacement accounting for collisions
	 * @return a new Vec3dd representing the displacement after fluid walking is accounted for
	 */
	@ModifyVariable(method = "move", ordinal = 1, index = 3, name = "vec3d", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/Entity;adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"))
	private Vec3d fluidCollision(Vec3d originalDisplacement) {
		// We only support living entities
		if (!((Object) this instanceof LivingEntity entity)) {
			return originalDisplacement;
		}

		// A bunch of checks to see if fluid walking is even possible
		if (originalDisplacement.y <= 0.0 && !isTouchingFluid(entity,entity.getBoundingBox().contract(0.001D))) {
			Map<Vec3d, Double> points = findFluidDistances(entity, originalDisplacement);
			Double highestDistance = null;

			for (Map.Entry<Vec3d, Double> point : points.entrySet()) {
				if (highestDistance == null || (point.getValue() != null && point.getValue() > highestDistance)) {
					highestDistance = point.getValue();
				}
			}

			if (highestDistance != null) {
				Vec3d finalDisplacement = new Vec3d(originalDisplacement.x, highestDistance, originalDisplacement.z);
				Box finalBox = entity.getBoundingBox().offset(finalDisplacement).contract(0.001D);
				if (isTouchingFluid(entity, finalBox)) {
					return originalDisplacement;
				} else {
					entity.fallDistance = 0.0F;
					entity.setOnGround(true);
					return finalDisplacement;
				}
			}
		}

		return originalDisplacement;
	}

	/**
	 * Gives the entity's distance to various fluids underneath (and above) it, in terms of the four bottom points
	 * of its bounding box.
	 *
	 * @param entity the entity to check for fluid walking.
	 * @param originalDisplacement the entity's proposed displacement after checking for collisions.
	 * @return a map containing each bottom corner of the entity's original displaced bounding box, and the distance
	 * between that corner and a given fluid, with a value of null for points with no fluid in range.
	 */
	@Unique
	private static Map<Vec3d, Double> findFluidDistances(LivingEntity entity, Vec3d originalDisplacement) {
		Box box = entity.getBoundingBox().offset(originalDisplacement);

		HashMap<Vec3d, Double> points = new HashMap<>();
		points.put(new Vec3d(box.minX, box.minY, box.minZ), null);
		points.put(new Vec3d(box.minX, box.minY, box.maxZ), null);
		points.put(new Vec3d(box.maxX, box.minY, box.minZ), null);
		points.put(new Vec3d(box.maxX, box.minY, box.maxZ), null);

		double fluidStepHeight = entity.isOnGround() ? Math.max(1.0, entity.stepHeight) : 0.0;

		for (Map.Entry<Vec3d, Double> entry : points.entrySet()) {
			for (int i = 0; ; i--) { // Check successive blocks downward
				// Auto step is essentially just shifting the fall adjustment up by the step height
				BlockPos landingPos = new BlockPos(entry.getKey()).add(0.0, i + fluidStepHeight, 0.0);
				FluidState landingState = entity.getWorld().getFluidState(landingPos);

				double distanceToFluidSurface = landingPos.getY() + landingState.getHeight() - entity.getY();
				double limitingVelocity = originalDisplacement.y;

				if (distanceToFluidSurface < limitingVelocity || distanceToFluidSurface > fluidStepHeight) {
					break;
				}

				if (!landingState.isEmpty() && FluidCollisionCallback.EVENT.invoker().collide(entity, landingState)) {
					entry.setValue(distanceToFluidSurface);
					break;
				}
			}
		}

		return points;
	}

	/**
	 * Checks if a given entity's bounding box is touching any fluids. This is modified vanilla code that works for
	 * any fluid, since vanilla only has one for water.
	 *
	 * @param entity the entity to check for fluid walking
	 * @param box the entity's proposed bounding box to check
	 * @return whether the entity's proposed bounding box will touch any fluids
	 */
	@Unique
	private static boolean isTouchingFluid(LivingEntity entity, Box box) {
		int minX = MathHelper.floor(box.minX);
		int maxX = MathHelper.ceil(box.maxX);
		int minY = MathHelper.floor(box.minY);
		int maxY = MathHelper.ceil(box.maxY);
		int minZ = MathHelper.floor(box.minZ);
		int maxZ = MathHelper.ceil(box.maxZ);
		World world = entity.getEntityWorld();

		@SuppressWarnings("deprecation") // isRegionLoaded does not exist in ChunkManager, so I have to use this
        var loaded = world.isRegionLoaded(minX, minY, minZ, maxX, maxY, maxZ);
		
        if (loaded) {
			BlockPos.Mutable mutable = new BlockPos.Mutable();

			// Loop over coords in bounding box
			for (int i = minX; i < maxX; ++i) {
				for (int j = minY; j < maxY; ++j) {
					for (int k = minZ; k < maxZ; ++k) {
						mutable.set(i, j, k);
						FluidState fluidState = world.getFluidState(mutable);

						if (!fluidState.isEmpty()) {
							double surfaceY = fluidState.getHeight(world, mutable) + j;

							if (surfaceY >= box.minY) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
}
