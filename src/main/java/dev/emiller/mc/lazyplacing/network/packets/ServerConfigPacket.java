package dev.emiller.mc.lazyplacing.network.packets;

import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import dev.emiller.mc.lazyplacing.network.packets.client.ServerConfigPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ServerConfigPacket {
    public ServerConfig config;

    public ServerConfigPacket(ServerConfig config) {
        this.config = config;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(config.stablePlacingDuration);
        buf.writeInt(config.maxRandomAdditionDuration);
    }

    public static ServerConfigPacket decode(FriendlyByteBuf ignoredBuf) {
        return new ServerConfigPacket(
            new ServerConfig(
                ignoredBuf.readInt(),
                ignoredBuf.readInt()
            )
        );
    }

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ServerConfigPacketHandler.handle(
                this, ctx)));

        ctx.setPacketHandled(true);
    }
}
