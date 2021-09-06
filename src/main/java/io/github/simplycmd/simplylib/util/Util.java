package io.github.simplycmd.simplylib.util;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.io.IOException;
import java.net.URL;

public class Util {
	// TODO: automatic mixin implementation
	public static ConfiguredFeature<?, ?> registerFeature(String mod_id, String id, Integer chance, Feature<DefaultFeatureConfig> feature_class) {
		// ALERT! ALERT! MAKE SURE TO ADD A MIXIN FOR THIS TO WORK! ALERT! ALERT! PLEASE SEE https://fabricmc.net/wiki/tutorial:features?rev=1599388928

		Registry.register(Registry.FEATURE, new Identifier(mod_id, id), feature_class);
		ConfiguredFeature<?, ?> FEATURE_CONFIGURED = feature_class.configure(FeatureConfig.DEFAULT).decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(chance)));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(mod_id, id), FEATURE_CONFIGURED);

		return FEATURE_CONFIGURED;
	}

	// TODO: registerOre

	public static SoundEvent registerSound(String mod_id, String id) {
		Identifier SOUND_ID = new Identifier(mod_id + ":" + id);
		SoundEvent SOUND = new SoundEvent(SOUND_ID);
		Registry.register(Registry.SOUND_EVENT, SOUND_ID, SOUND);
		return SOUND;
	}

	public static ArrowEntity initArrow(Item arrow, Potion potion, Vec3d pos, float speed, Vec3d direction, PersistentProjectileEntity.PickupPermission pickupPermission, World world) {
		// Create arrow
		ArrowEntity arrowEntity = new ArrowEntity(world, pos.getX(), pos.getY(), pos.getZ());
		if (potion != null) arrowEntity.initFromStack(PotionUtil.setPotion(arrow.getDefaultStack(), potion));
		arrowEntity.pickupType = pickupPermission;
		arrowEntity.updateVelocity(speed, direction);
		return arrowEntity;
	}

	public static NativeImageBackedTexture imageFromURL(URL url, int width, int height) {
		NativeImage image = null;
		try {
			image = NativeImage.read(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new NativeImageBackedTexture(parseImage(image, width, height));
	}

	private static NativeImage parseImage(NativeImage image, int width, int height) {
		int imageSrcWidth = image.getWidth();
		int srcHeight = image.getHeight();

		for (int imageSrcHeight = image.getHeight(); width < imageSrcWidth
				|| height < imageSrcHeight; height *= 2) {
			width *= 2;
		}

		NativeImage imgNew = new NativeImage(width, height, true);
		for (int x = 0; x < imageSrcWidth; x++) {
			for (int y = 0; y < srcHeight; y++) {
				imgNew.setPixelColor(x, y, image.getPixelColor(x, y));
			}
		}
		image.close();
		return imgNew;
	}
}
