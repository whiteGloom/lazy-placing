package dev.emiller.mc.lazyplacing.bridge;

import dev.emiller.mc.lazyplacing.libs.LazyPlacingContext;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;

public interface PlayerEntityMixinInterface {
    void lazyPlacing$onTryToPlaceBlock(BlockItem originalBlockReference, UseOnContext itemUsageContext);

    void lazyPlacing$cleanPlacingContext();

    @Nullable LazyPlacingContext lazyPlacing$getLazyPlacingContext();
}