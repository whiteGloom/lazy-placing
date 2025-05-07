package dev.emiller.mc.lazyplacing.mixin.client;

import dev.emiller.mc.lazyplacing.mixin.common.PlayerEntityMixin;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class ClientPlayerEntityMixin extends PlayerEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void lazyPlacing$playerTick(CallbackInfo ci) {
        lazyPlacing$onEntityTick();
    }
}