package dev.emiller.mc.lazyplacing.network.packets;

import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import dev.emiller.mc.lazyplacing.network.packets.client.ServerConfigPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerConfigPacket {
    public ServerConfig config;

    public ServerConfigPacket(ServerConfig config) {
        this.config = config;
    }

    public static void encode(ServerConfigPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.config.stablePlacingDuration);
        buf.writeInt(msg.config.maxRandomAdditionDuration);
    }

    public static ServerConfigPacket decode(FriendlyByteBuf ignoredBuf) {
        return new ServerConfigPacket(
            new ServerConfig(
                ignoredBuf.readInt(),
                ignoredBuf.readInt()
            )
        );
    }

    public static void handle(ServerConfigPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ServerConfigPacketHandler.handle(
                msg, ctx)));

        ctx.get().setPacketHandled(true);
    }
}
