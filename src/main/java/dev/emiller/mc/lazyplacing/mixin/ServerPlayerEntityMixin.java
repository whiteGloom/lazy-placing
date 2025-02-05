package dev.emiller.mc.lazyplacing.mixin;

import dev.emiller.mc.lazyplacing.LazyPlacing;
import dev.emiller.mc.lazyplacing.lib.LazyPlacingConfig;
import dev.emiller.mc.lazyplacing.lib.BlockPlacingContext;
import dev.emiller.mc.lazyplacing.mbridge.ServerPlayerEntityMixinInterface;
import dev.emiller.mc.lazyplacing.network.LazyPlacingNetwork;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityMixinInterface {
    @Unique
    public BlockPlacingContext blockPlacingContext;

    @Inject(method = "playerTick", at = @At("TAIL"))
    public void lazyPlacing$playerTick(CallbackInfo ci) {
        ServerPlayerEntity playerReference = (ServerPlayerEntity) (Object) this;

        if (blockPlacingContext != null) {
            if (blockPlacingContext.didPositionChangedTooMuch(playerReference)) {
                LazyPlacingNetwork.sendPlacingProgress(0, playerReference);

                blockPlacingContext = null;
            } else {
                blockPlacingContext.tick();
                float progress = blockPlacingContext.getProgress();

                if (blockPlacingContext.isDone()) {
                    blockPlacingContext = null;
                    progress = 0;
                }

                LazyPlacingNetwork.sendPlacingProgress(progress, playerReference);
            }
        }
    }

    public void lazyPlacing$onTryToPlaceBlock(BlockItem originalBlockReference, ItemUsageContext itemUsageContext) {
        ServerPlayerEntity playerReference = (ServerPlayerEntity) (Object) this;

        Random random = new Random();
        LazyPlacingConfig config = LazyPlacing.CONFIG;

        int stableDurationPart = config.stablePlacingDuration;
        int flexibleDurationPart = config.maxRandomAdditionDuration > 0 ? random.nextInt(config.maxRandomAdditionDuration) : 0;

        this.blockPlacingContext = new BlockPlacingContext(
                originalBlockReference,
                itemUsageContext,
                stableDurationPart + flexibleDurationPart,
                playerReference.getPos()
        );
    }
}