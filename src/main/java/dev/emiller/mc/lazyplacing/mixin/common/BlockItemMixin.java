package dev.emiller.mc.lazyplacing.mixin.common;

import dev.emiller.mc.lazyplacing.bridge.PlayerEntityMixinInterface;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void lazyPlacing$useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();

        if (player != null && player.isCreative()) {
            return;
        }

        BlockItem originalBlockReference = ((BlockItem) (Object) this);

        if (!originalBlockReference.getBlock().isEnabled(context.getLevel().enabledFeatures())) {
            cir.setReturnValue(InteractionResult.FAIL);
            return;
        } else if (!new BlockPlaceContext(context).canPlace()) {
            cir.setReturnValue(InteractionResult.FAIL);
            return;
        }


        if (player instanceof PlayerEntityMixinInterface p) {
            p.lazyPlacing$onTryToPlaceBlock(originalBlockReference, context);
        }

        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}