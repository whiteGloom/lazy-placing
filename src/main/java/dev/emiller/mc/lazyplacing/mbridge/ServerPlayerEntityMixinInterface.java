package dev.emiller.mc.lazyplacing.mbridge;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;

public interface ServerPlayerEntityMixinInterface {
    void lazyPlacing$onTryToPlaceBlock(BlockItem originalBlockReference, ItemUsageContext itemUsageContext);
}