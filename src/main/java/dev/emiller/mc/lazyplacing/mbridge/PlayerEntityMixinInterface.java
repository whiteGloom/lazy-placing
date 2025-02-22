package dev.emiller.mc.lazyplacing.mbridge;

import dev.emiller.mc.lazyplacing.lib.LazyPlacingContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import org.jetbrains.annotations.Nullable;

public interface PlayerEntityMixinInterface {
    void lazyPlacing$onTryToPlaceBlock(BlockItem originalBlockReference, ItemUsageContext itemUsageContext);

    void lazyPlacing$cleanPlacingContext();

    @Nullable LazyPlacingContext lazyPlacing$getLazyPlacingContext();
}