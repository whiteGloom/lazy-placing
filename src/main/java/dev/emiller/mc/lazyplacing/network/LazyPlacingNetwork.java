package dev.emiller.mc.lazyplacing.network;

import dev.emiller.mc.lazyplacing.LazyPlacingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class LazyPlacingNetwork {
    public static void sendPlacingProgress(float progress, ServerPlayerEntity playerReference) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeFloat(progress);

        ServerPlayNetworking.send(playerReference, LazyPlacingConstants.BLOCK_PLACING_PROGRESS_UPDATE_PACKET_ID, buf);
    }
}