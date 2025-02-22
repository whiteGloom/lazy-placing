package dev.emiller.mc.lazyplacing.mixin;

import dev.emiller.mc.lazyplacing.network.LazyPlacingNetwork;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin extends PlayerEntityMixin {
    @Inject(method = "playerTick", at = @At("TAIL"))
    public void lazyPlacing$playerTick(CallbackInfo ci) {
        lazyPlacing$onEntityTick();
    }

    @Override
    public void onPlacingFailed() {
        ServerPlayerEntity playerReference = (ServerPlayerEntity) (Object) this;
        LazyPlacingNetwork.sendPlacingClear(playerReference);

        super.onPlacingFailed();
    }
}
