package dev.emiller.mc.lazyplacing.mixin;

import dev.emiller.mc.lazyplacing.LazyPlacing;
import dev.emiller.mc.lazyplacing.lib.LazyPlacingConfig;
import dev.emiller.mc.lazyplacing.lib.LazyPlacingContext;
import dev.emiller.mc.lazyplacing.mbridge.PlayerEntityMixinInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityMixinInterface {
    @Unique
    public LazyPlacingContext lazyPlacingContext;


    @Unique
    public void lazyPlacing$onTryToPlaceBlock(BlockItem originalBlockReference, ItemUsageContext itemUsageContext) {
        PlayerEntity playerReference = (PlayerEntity) (Object) this;

        Random random = new Random();
        LazyPlacingConfig config = LazyPlacing.CONFIG;

        int stableDurationPart = config.stablePlacingDuration;
        int flexibleDurationPart = config.maxRandomAdditionDuration > 0 ?
                random.nextInt(config.maxRandomAdditionDuration) : 0;

        this.lazyPlacingContext = new LazyPlacingContext(
                originalBlockReference,
                itemUsageContext,
                stableDurationPart + flexibleDurationPart,
                playerReference.getPos()
        );
    }

    @Unique
    public void lazyPlacing$cleanPlacingContext() {
        this.lazyPlacingContext = null;
    }

    @Unique
    public void lazyPlacing$onEntityTick() {
        PlayerEntity playerReference = (PlayerEntity) (Object) this;

        if (lazyPlacingContext != null) {
            ItemPlacementContext placementContext = new ItemPlacementContext(lazyPlacingContext.itemUsageContext);

            if (lazyPlacingContext.didPositionChangedTooMuch(playerReference)) {
                lazyPlacing$cleanPlacingContext();
            } else {
                lazyPlacingContext.tick();

                if (lazyPlacingContext.isDone) {
                    lazyPlacingContext.originalBlockReference.place(placementContext);

                    lazyPlacingContext = null;
                }
            }
        }
    }

    @Unique
    public void onPlacingFailed() {
        lazyPlacing$cleanPlacingContext();
    }

    @Unique
    public LazyPlacingContext lazyPlacing$getLazyPlacingContext() {
        return lazyPlacingContext;
    }
}
