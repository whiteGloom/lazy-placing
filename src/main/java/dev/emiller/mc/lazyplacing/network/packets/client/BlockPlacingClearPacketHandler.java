package dev.emiller.mc.lazyplacing.network.packets.client;

import dev.emiller.mc.lazyplacing.bridge.PlayerEntityMixinInterface;
import dev.emiller.mc.lazyplacing.network.packets.BlockPlacingClearPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;

@OnlyIn(Dist.CLIENT)
public class BlockPlacingClearPacketHandler {
    public static void handle(BlockPlacingClearPacket ignoredMsg, CustomPayloadEvent.Context ignoredCtx) {
        if (Minecraft.getInstance().player instanceof PlayerEntityMixinInterface p) {
            p.lazyPlacing$cleanPlacingContext();
        }
    }
}
