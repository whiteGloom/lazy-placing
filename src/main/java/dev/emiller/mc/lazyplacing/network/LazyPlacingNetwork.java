package dev.emiller.mc.lazyplacing.network;

import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import dev.emiller.mc.lazyplacing.network.packets.BlockPlacingClearPacket;
import dev.emiller.mc.lazyplacing.network.packets.ServerConfigPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class LazyPlacingNetwork {
    protected static int id = 1;
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath("lazyplacing", "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(
            id++,
            BlockPlacingClearPacket.class,
            BlockPlacingClearPacket::encode,
            BlockPlacingClearPacket::decode,
            BlockPlacingClearPacket::handle
        );

        INSTANCE.registerMessage(
            id++,
            ServerConfigPacket.class,
            ServerConfigPacket::encode,
            ServerConfigPacket::decode,
            ServerConfigPacket::handle
        );
    }

    public static void sendPlacingClear(ServerPlayer playerReference) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerReference), new BlockPlacingClearPacket());
    }

    public static void sendConfigToPlayer(ServerPlayer playerReference, ServerConfig config) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerReference), new ServerConfigPacket(config));
    }
}