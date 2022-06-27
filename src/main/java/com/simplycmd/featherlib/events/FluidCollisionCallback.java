package com.simplycmd.featherlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;

// All credit for the following code goes to https://github.com/florensie/ExpandAbility, which is under the MIT license.
public interface FluidCollisionCallback {
	Event<FluidCollisionCallback> EVENT = EventFactory.createArrayBacked(FluidCollisionCallback.class,
			(listeners) -> (player, fluidState) -> {
				for (FluidCollisionCallback listener : listeners) {
					if (listener.collide(player, fluidState)) {
						return true;
					}
				}
				return false;
			});

	/**
	 * @param entity The {@link LivingEntity} that is colliding
	 * @param fluidState The {@link FluidState} the entity is collding with
	 * @return true to enable solid fluid collisions and false for vanilla behaviour
	 */
	boolean collide(LivingEntity entity, FluidState fluidState);
}