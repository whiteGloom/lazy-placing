package dev.emiller.mc.lazyplacing.network;

import dev.emiller.mc.lazyplacing.LazyPlacingConstants;
import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class LazyPlacingNetwork {
    public static void sendPlacingClear(ServerPlayerEntity playerReference) {
        PacketByteBuf buf = PacketByteBufs.create();

        ServerPlayNetworking.send(playerReference, LazyPlacingConstants.BLOCK_PLACING_CLEAR_PACKET_ID, buf);
    }

    public static void sendConfigToPlayer(ServerPlayerEntity playerReference, ServerConfig config) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeInt(config.stablePlacingDuration);
        buf.writeInt(config.maxRandomAdditionDuration);

        ServerPlayNetworking.send(playerReference, LazyPlacingConstants.HOST_CONFIG_PACKET_ID, buf);
    }
}