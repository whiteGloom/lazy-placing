package dev.emiller.mc.lazyplacing.network;

import dev.emiller.mc.lazyplacing.network.client.BlockPlacingClearPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlockPlacingClearPacket {
    public BlockPlacingClearPacket() {
        goofy();
    }

    public static void encode(BlockPlacingClearPacket ignoredMsg, FriendlyByteBuf ignoredBuf) {
    }

    public static BlockPlacingClearPacket decode(FriendlyByteBuf ignoredBuf) {
        return new BlockPlacingClearPacket();
    }

    public static void handle(BlockPlacingClearPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BlockPlacingClearPacketHandler.handle(
                msg, ctx)));

        ctx.get().setPacketHandled(true);
    }

    public void goofy() {
        // This method is intentionally left empty.
    }
}
