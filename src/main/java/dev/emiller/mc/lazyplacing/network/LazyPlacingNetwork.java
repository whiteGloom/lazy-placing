package dev.emiller.mc.lazyplacing.network;

import dev.emiller.mc.lazyplacing.LazyPlacing;
import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import dev.emiller.mc.lazyplacing.network.packets.BlockPlacingClearPacket;
import dev.emiller.mc.lazyplacing.network.packets.ServerConfigPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class LazyPlacingNetwork {
    private static final int PROTOCOL_VERSION = 1;

    public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(LazyPlacing.MODID, "main"))
                                                               .serverAcceptedVersions((status, version) -> true)
                                                               .clientAcceptedVersions((status, version) -> true)
                                                               .networkProtocolVersion(PROTOCOL_VERSION)
                                                               .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(BlockPlacingClearPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(BlockPlacingClearPacket::encode)
                .decoder(BlockPlacingClearPacket::decode)
                .consumerMainThread(BlockPlacingClearPacket::handle)
                .add();

        INSTANCE.messageBuilder(ServerConfigPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ServerConfigPacket::encode)
                .decoder(ServerConfigPacket::decode)
                .consumerMainThread(ServerConfigPacket::handle)
                .add();
    }

    public static void sendPlacingClear(ServerPlayer playerReference) {
        INSTANCE.send(new BlockPlacingClearPacket(), PacketDistributor.PLAYER.with(playerReference));
    }

    public static void sendConfigToPlayer(ServerPlayer playerReference, ServerConfig config) {
        INSTANCE.send(new ServerConfigPacket(config), PacketDistributor.PLAYER.with(playerReference));
    }
}