package dev.emiller.mc.lazyplacing.mixin.client;

import dev.emiller.mc.lazyplacing.mixin.PlayerEntityMixin;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends PlayerEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void lazyPlacing$playerTick(CallbackInfo ci) {
        lazyPlacing$onEntityTick();
    }
}