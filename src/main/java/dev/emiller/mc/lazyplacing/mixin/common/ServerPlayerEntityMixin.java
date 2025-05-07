package dev.emiller.mc.lazyplacing.mixin.common;

import dev.emiller.mc.lazyplacing.network.LazyPlacingNetwork;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin extends PlayerEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void lazyPlacing$playerTick(CallbackInfo ci) {
        lazyPlacing$onEntityTick();
    }

    @Override
    public void lazyPlacing$onPlacingFailed() {
        ServerPlayer playerReference = (ServerPlayer) (Object) this;
        LazyPlacingNetwork.sendPlacingClear(playerReference);

        super.lazyPlacing$onPlacingFailed();
    }
}
