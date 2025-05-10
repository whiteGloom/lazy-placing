package dev.emiller.mc.lazyplacing.mixin;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import dev.emiller.mc.lazyplacing.configs.ServerConfig;
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
        ServerConfig config = LazyPlacingConfigs.hostConfig;

        if (config == null) {
            return;
        }

        PlayerEntity playerReference = (PlayerEntity) (Object) this;

        Random random = new Random();

        int stableDurationPart = config.stablePlacingDuration;
        int flexibleDurationPart = config.maxRandomAdditionDuration > 0 ? random.nextInt(config.maxRandomAdditionDuration) : 0;

        this.lazyPlacingContext = new LazyPlacingContext(
                originalBlockReference,
                itemUsageContext,
                stableDurationPart + flexibleDurationPart,
                playerReference.getPos()
        );
    }

    @Unique
    public void lazyPlacing$clearPlacingContext() {
        this.lazyPlacingContext = null;
    }

    @Unique
    public void lazyPlacing$onEntityTick() {
        PlayerEntity playerReference = (PlayerEntity) (Object) this;

        if (lazyPlacingContext != null) {
            ItemPlacementContext placementContext = new ItemPlacementContext(lazyPlacingContext.itemUsageContext);

            if (lazyPlacingContext.didPositionChangedTooMuch(playerReference)) {
                onPlacingFailed();
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
        lazyPlacing$clearPlacingContext();
    }

    @Unique
    public LazyPlacingContext lazyPlacing$getLazyPlacingContext() {
        return lazyPlacingContext;
    }
}
