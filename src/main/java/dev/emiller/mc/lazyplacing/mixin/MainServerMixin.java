package dev.emiller.mc.lazyplacing.mixin;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MainServerMixin {
    @Inject(method = "runServer", at = @At("HEAD"))
    protected void lazyPlacing$runServer(CallbackInfo ci) {
        LazyPlacingConfigs.loadServerConfig();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    protected void lazyPlacing$shutdown(CallbackInfo ci) {
        LazyPlacingConfigs.clearServerConfig();
    }
}
