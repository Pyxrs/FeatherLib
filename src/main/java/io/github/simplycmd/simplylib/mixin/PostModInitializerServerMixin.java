package io.github.simplycmd.simplylib.mixin;

import io.github.simplycmd.simplylib.PostMain;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftDedicatedServer.class)
    public class PostModInitializerServerMixin {

        @Inject(method = "setupServer", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", ordinal = 0, remap = false))
        public void runPostInitialize(CallbackInfoReturnable cir) {
            //FabricLoader.getInstance().getEntrypoints(Main.MOD_ID, PostModInitializer.class).forEach(PostModInitializer::onPostInitialize);
            //Temporary solution until I get an entrypoint
            PostMain.onPostInitialize();
        }
    }