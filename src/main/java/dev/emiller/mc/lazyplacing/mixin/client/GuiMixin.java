package dev.emiller.mc.lazyplacing.mixin.client;

import dev.emiller.mc.lazyplacing.libs.LazyPlacingContext;
import dev.emiller.mc.lazyplacing.bridge.PlayerEntityMixinInterface;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public class GuiMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void lazyPlacing$render(GuiGraphics context, float tickDelta, CallbackInfo ci) {
        ForgeGui it = (ForgeGui) (Object) this;

        float val = 0;

        if (it.getMinecraft().player instanceof PlayerEntityMixinInterface player) {
            LazyPlacingContext lazyContext = player.lazyPlacing$getLazyPlacingContext();

            if (lazyContext != null) {
                val = lazyContext.getProgress();
            }
        }

        if (val != 0 && val != 1) {
            float width = context.guiWidth();
            float height = context.guiHeight();
            int xCenter = (int) width / 2;
            int yCenter = (int) height / 2;

            int xWidthTotal = 40;
            int xStart = xCenter - xWidthTotal / 2;
            int yStart = (int) (yCenter * 1.35);

            int spacer = 2;
            int xWidthCurrent = (int) ((xWidthTotal - spacer) * val);
            int yHeight = 5;

            // Outer half-transparent black box
            context.fill(xStart, yStart, xStart + xWidthTotal, yStart + yHeight, 0x7A000000);

            // Inner white progressbar
            context.fill(
                    xStart + spacer,
                    yStart + spacer,
                    xStart + xWidthCurrent,
                    yStart + yHeight - spacer,
                    0xFFFFFFFF
            );
        }
    }
}
