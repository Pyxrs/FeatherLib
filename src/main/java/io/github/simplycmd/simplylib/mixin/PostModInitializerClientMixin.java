package io.github.simplycmd.simplylib.mixin;

import io.github.simplycmd.simplylib.PostMain;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class PostModInitializerClientMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setPhase(Ljava/lang/String;)V", ordinal = 0))
    public void runPostInitialize(CallbackInfo ci) {
        //FabricLoader.getInstance().getEntrypoints(Main.MOD_ID, PostModInitializer.class).forEach(PostModInitializer::onPostInitialize);
        //Temporary solution until I get an entrypoint
        PostMain.onPostInitialize();
    }
}