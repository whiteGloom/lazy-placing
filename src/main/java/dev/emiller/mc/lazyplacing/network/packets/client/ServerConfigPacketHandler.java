package dev.emiller.mc.lazyplacing.network.packets.client;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import dev.emiller.mc.lazyplacing.network.packets.ServerConfigPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ServerConfigPacketHandler {
    public static void handle(ServerConfigPacket msg, Supplier<NetworkEvent.Context> ignoredCtx) {
        LazyPlacingConfigs.setHostConfig(msg.config);
    }
}
