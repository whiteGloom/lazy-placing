package dev.emiller.mc.lazyplacing.network.packets;

import dev.emiller.mc.lazyplacing.network.packets.client.BlockPlacingClearPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class BlockPlacingClearPacket {
    public BlockPlacingClearPacket() {
        goofy();
    }

    public void encode(FriendlyByteBuf ignoredBuf) {
    }

    public static BlockPlacingClearPacket decode(FriendlyByteBuf ignoredBuf) {
        return new BlockPlacingClearPacket();
    }

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BlockPlacingClearPacketHandler.handle(
                this, ctx)));

        ctx.setPacketHandled(true);
    }

    public void goofy() {
        // This method is intentionally left empty.
    }
}
