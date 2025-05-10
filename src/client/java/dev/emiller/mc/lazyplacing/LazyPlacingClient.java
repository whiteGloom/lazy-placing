package dev.emiller.mc.lazyplacing;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import dev.emiller.mc.lazyplacing.mbridge.PlayerEntityMixinInterface;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class LazyPlacingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(
                LazyPlacingConstants.BLOCK_PLACING_CLEAR_PACKET_ID, (client, handler, buf, responseSender) -> {
                    if (client.player instanceof PlayerEntityMixinInterface player) {
                        player.lazyPlacing$clearPlacingContext();
                    }
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                LazyPlacingConstants.HOST_CONFIG_PACKET_ID,
                (client, handler, buf, responseSender) -> LazyPlacingConfigs.setHostConfig(new ServerConfig(
                        buf.readInt(),
                        buf.readInt()
                ))
        );
    }
}