package dev.emiller.mc.lazyplacing.mixin.client;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    public void lazyPlacing$disconnect(Screen screen, CallbackInfo ci) {
        LazyPlacingConfigs.clearHostConfig();
    }
}
