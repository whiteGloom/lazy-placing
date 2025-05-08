package dev.emiller.mc.lazyplacing.mixin.common;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import dev.emiller.mc.lazyplacing.configs.ServerConfig;
import dev.emiller.mc.lazyplacing.libs.LazyPlacingContext;
import dev.emiller.mc.lazyplacing.bridge.PlayerEntityMixinInterface;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(Player.class)
public class PlayerEntityMixin implements PlayerEntityMixinInterface {
    @Unique
    public LazyPlacingContext lazyPlacing$lazyPlacingContext;

    @Unique
    public void lazyPlacing$onTryToPlaceBlock(BlockItem originalBlockReference, UseOnContext itemUsageContext) {
        ServerConfig config = LazyPlacingConfigs.hostConfig;

        if (config == null) {
            return;
        }

        Player playerReference = (Player) (Object) this;

        Random random = new Random();

        int stableDurationPart = config.stablePlacingDuration;
        int flexibleDurationPart = config.maxRandomAdditionDuration > 0 ?
                random.nextInt(config.maxRandomAdditionDuration) : 0;

        this.lazyPlacing$lazyPlacingContext = new LazyPlacingContext(
                originalBlockReference,
                itemUsageContext,
                stableDurationPart + flexibleDurationPart,
                playerReference.position()
        );
    }

    @Unique
    public void lazyPlacing$cleanPlacingContext() {
        this.lazyPlacing$lazyPlacingContext = null;
    }

    @Unique
    public void lazyPlacing$onEntityTick() {
        Player playerReference = (Player) (Object) this;

        if (lazyPlacing$lazyPlacingContext != null) {
            BlockPlaceContext placementContext = new BlockPlaceContext(lazyPlacing$lazyPlacingContext.itemUsageContext);

            if (lazyPlacing$lazyPlacingContext.didPositionChangedTooMuch(playerReference)) {
                lazyPlacing$onPlacingFailed();
            } else {
                lazyPlacing$lazyPlacingContext.tick();

                if (lazyPlacing$lazyPlacingContext.isDone) {
                    lazyPlacing$lazyPlacingContext.originalBlockReference.place(placementContext);

                    lazyPlacing$lazyPlacingContext = null;
                }
            }
        }
    }

    @Unique
    public void lazyPlacing$onPlacingFailed() {
        lazyPlacing$cleanPlacingContext();
    }

    @Unique
    public LazyPlacingContext lazyPlacing$getLazyPlacingContext() {
        return lazyPlacing$lazyPlacingContext;
    }
}
