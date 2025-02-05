package dev.emiller.mc.lazyplacing.mixin;

import dev.emiller.mc.lazyplacing.mbridge.ServerPlayerEntityMixinInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void lazyPlacing$useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();

        if (player instanceof ServerPlayerEntity sp) {
            if (sp.interactionManager.getGameMode() != GameMode.SURVIVAL) {
                return;
            }
        }

        BlockItem originalBlockReference = ((BlockItem) (Object) this);

        if (!originalBlockReference.getBlock().isEnabled(context.getWorld().getEnabledFeatures())) {
            cir.setReturnValue(ActionResult.FAIL);
            return;
        } else if (!new ItemPlacementContext(context).canPlace()) {
            cir.setReturnValue(ActionResult.FAIL);
            return;
        }

        World world = context.getWorld();

        if (world.isClient()) {
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }


        if (player instanceof ServerPlayerEntityMixinInterface p) {
            if (world.getServer() != null) {
                p.lazyPlacing$onTryToPlaceBlock(originalBlockReference, context);
            }
        }

        cir.setReturnValue(ActionResult.SUCCESS);
    }
}