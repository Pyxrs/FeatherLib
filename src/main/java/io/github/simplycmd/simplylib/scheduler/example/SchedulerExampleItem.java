package io.github.simplycmd.simplylib.scheduler.example;

import io.github.simplycmd.simplylib.scheduler.ServerScheduler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SchedulerExampleItem extends Item {
    public SchedulerExampleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ServerScheduler.schedule(100, () -> System.out.println("Printed 100 ticks or 5 seconds later"));
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
