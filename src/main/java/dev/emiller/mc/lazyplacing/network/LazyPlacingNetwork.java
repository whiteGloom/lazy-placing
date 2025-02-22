package dev.emiller.mc.lazyplacing.network;

import dev.emiller.mc.lazyplacing.LazyPlacingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class LazyPlacingNetwork {
    public static void sendPlacingClear(ServerPlayerEntity playerReference) {
        PacketByteBuf buf = PacketByteBufs.create();

        ServerPlayNetworking.send(playerReference, LazyPlacingConstants.BLOCK_PLACING_CLEAR_PACKET_ID, buf);
    }
}