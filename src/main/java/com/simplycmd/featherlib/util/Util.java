package com.simplycmd.featherlib.util;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ScoreboardCriterionArgumentType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stat;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Util implements ModInitializer {
	
	// TODO: registerFeature

	// TODO: registerOre

	/**
	 * Easily register a sound
	 * @param id In-game sound id
	 * @return Sound event (to play the sound through code)
	 */
	public static SoundEvent registerSound(Identifier id) {
		Identifier SOUND_ID = new Identifier(id.getNamespace() + ":" + id.getPath());
		SoundEvent SOUND = new SoundEvent(SOUND_ID);
		Registry.register(Registry.SOUND_EVENT, SOUND_ID, SOUND);
		return SOUND;
	}

	/**
	 * Easily spawn an arrow
	 * @param arrow Your arrow item
	 * @param potion Tipped arrow effect (optional)
	 * @param pos
	 * @param speed
	 * @param direction
	 * @param pickupPermission Whether or not the arrow can be picked up
	 * @param world
	 * @return An arrow entity that can be spawned
	 */
	public static ArrowEntity initArrow(Item arrow, Potion potion, Vec3d pos, float speed, Vec3d direction, PersistentProjectileEntity.PickupPermission pickupPermission, World world) {
		// Create arrow
		ArrowEntity arrowEntity = new ArrowEntity(world, pos.getX(), pos.getY(), pos.getZ());
		if (potion != null) arrowEntity.initFromStack(PotionUtil.setPotion(arrow.getDefaultStack(), potion));
		arrowEntity.pickupType = pickupPermission;
		arrowEntity.updateVelocity(speed, direction);
		return arrowEntity;
	}
	public static ArrowEntity initArrow(Item arrow, Vec3d pos, float speed, Vec3d direction, PersistentProjectileEntity.PickupPermission pickupPermission, World world) {
		return initArrow(arrow, null, pos, speed, direction, pickupPermission, world);
	}

	/**
	 * Converts a url image into a {@link net.minecraft.client.texture.NativeImageBackedTexture}
	 * @param url
	 * @return
	 */
	public static NativeImage imageFromURL(URL url) {
		NativeImage image = null;
		try {
			image = NativeImage.read(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Really simple {@link net.minecraft.util.math.BlockPos}-based teleport
	 * @param player
	 * @param position
	 */
	public static void teleport(ServerPlayerEntity player, BlockPos position) {
		player.teleport(player.getWorld(), (float) position.getX(), (float) position.getY(), (float) position.getZ(), 0, 0);
	}

	/**
	 * Cleaner way to add tooltips
	 * @param lines
	 * @return
	 */
	public static List<Text> tooltipLine(Text... lines) {
		List<Text> list = new ArrayList<Text>();
		for (Text line : lines) {
			list.add(line);
		}
		return list;
	}

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            dispatcher.register(CommandManager.literal("statistic")
			.then(CommandManager.argument("player", EntityArgumentType.player())
			.then(CommandManager.argument("type", ScoreboardCriterionArgumentType.scoreboardCriterion())
			.executes(context -> {
                var player = EntityArgumentType.getPlayer(context, "player");
				var type = ScoreboardCriterionArgumentType.getCriterion(context, "type");
				if (
					type.equals(ScoreboardCriterion.AIR) ||
					type.equals(ScoreboardCriterion.ARMOR) ||
					type.equals(ScoreboardCriterion.DEATH_COUNT) ||
					type.equals(ScoreboardCriterion.DUMMY) ||
					type.equals(ScoreboardCriterion.FOOD) ||
					type.equals(ScoreboardCriterion.HEALTH) ||
					type.equals(ScoreboardCriterion.KILLED_BY_TEAMS) ||
					type.equals(ScoreboardCriterion.LEVEL) ||
					type.equals(ScoreboardCriterion.PLAYER_KILL_COUNT) ||
					type.equals(ScoreboardCriterion.TEAM_KILLS) ||
					type.equals(ScoreboardCriterion.TOTAL_KILL_COUNT) ||
					type.equals(ScoreboardCriterion.TRIGGER) ||
					type.equals(ScoreboardCriterion.XP)
				) {
					context.getSource().sendError(Text.literal("Not a supported statistic type! Try something else."));
					return 0;
				} else {
					var result = player.getStatHandler().getStat((Stat<?>) type);
					context.getSource().sendFeedback(Text.literal(player.getDisplayName().getString() + "'s ").append(Text.translatable(type.getName())).append(Text.literal(" statistic is " + result)), false);
					return 1;
				}
            }))));
        });
	}
}
