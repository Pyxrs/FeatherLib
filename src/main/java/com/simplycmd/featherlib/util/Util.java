package com.simplycmd.featherlib.util;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
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

public class Util {

	// REMOVED BECAUSE 1.18
	// TODO: automatic mixin implementation
	//public static ConfiguredFeature<?, ?> registerFeature(String mod_id, String id, Integer chance, Feature<DefaultFeatureConfig> feature_class) {
	//	// ALERT! ALERT! MAKE SURE TO ADD A MIXIN FOR THIS TO WORK! ALERT! ALERT! PLEASE SEE https://fabricmc.net/wiki/tutorial:features?rev=1599388928

	//	Registry.register(Registry.FEATURE, new Identifier(mod_id, id), feature_class);
	//	ConfiguredFeature<?, ?> FEATURE_CONFIGURED = feature_class.configure(FeatureConfig.DEFAULT).decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(chance)));
	//	Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(mod_id, id), FEATURE_CONFIGURED);

	//	return FEATURE_CONFIGURED;
	//}

	// TODO: registerOre

	// Easily register a sound
	public static SoundEvent registerSound(Identifier id) {
		Identifier SOUND_ID = new Identifier(id.getNamespace() + ":" + id.getPath());
		SoundEvent SOUND = new SoundEvent(SOUND_ID);
		Registry.register(Registry.SOUND_EVENT, SOUND_ID, SOUND);
		return SOUND;
	}

	// Easily spawn an arrow
	public static ArrowEntity initArrow(Item arrow, Potion potion, Vec3d pos, float speed, Vec3d direction, PersistentProjectileEntity.PickupPermission pickupPermission, World world) {
		// Create arrow
		ArrowEntity arrowEntity = new ArrowEntity(world, pos.getX(), pos.getY(), pos.getZ());
		if (potion != null) arrowEntity.initFromStack(PotionUtil.setPotion(arrow.getDefaultStack(), potion));
		arrowEntity.pickupType = pickupPermission;
		arrowEntity.updateVelocity(speed, direction);
		return arrowEntity;
	}

	// For downloading images and converting them to NativeBackedTextures
	public static NativeImageBackedTexture imageFromURL(URL url, int width, int height) {
		NativeImage image = null;
		try {
			image = NativeImage.read(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new NativeImageBackedTexture(cropImage(image, width, height));
	}

	// For resizing images
	public static NativeImage cropImage(NativeImage image, int width, int height) {
		int imageSrcWidth = image.getWidth();
		int srcHeight = image.getHeight();

		for (int imageSrcHeight = image.getHeight(); width < imageSrcWidth
				|| height < imageSrcHeight; height *= 2) {
			width *= 2;
		}

		NativeImage imgNew = new NativeImage(width, height, true);
		for (int x = 0; x < imageSrcWidth; x++) {
			for (int y = 0; y < srcHeight; y++) {
				imgNew.setColor(x, y, image.getColor(x, y));
			}
		}
		image.close();
		return imgNew;
	}

	// Simplified teleport
	public static void teleport(ServerPlayerEntity player, BlockPos position) {
		player.teleport(player.getWorld(), (float) position.getX(), (float) position.getY(), (float) position.getZ(), 0, 0);
	}

	// Add tooltip
	public static List<Text> tooltipLine(Text ... lines) {
		List<Text> list = new ArrayList<Text>();
		for (Text line : lines) {
			list.add(line);
		}
		return list;
	}
}
