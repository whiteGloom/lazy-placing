package dev.emiller.mc.lazyplacing.mixin.common;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import dev.emiller.mc.lazyplacing.network.LazyPlacingNetwork;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    void lazyPlacing$placeNewPlayer(Connection ignoredConnection, ServerPlayer player, CallbackInfo ci) {
        LazyPlacingNetwork.sendConfigToPlayer(player, LazyPlacingConfigs.serverConfig);
    }
}