package dev.emiller.mc.lazyplacing.libs;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.BlockHitResult;


public class LazyPlacingContext {
    public int passedTicks = 0;
    public BlockItem originalBlockReference;
    public UseOnContext itemUsageContext;
    public Vec3 playerPositionReference;
    public int duration;
    public boolean isDone = false;

    public LazyPlacingContext(
            BlockItem originalBlockReference,
            UseOnContext itemUsageContext,
            int duration,
            Vec3 playerPositionReference
    ) {
        this.originalBlockReference = originalBlockReference;
        this.itemUsageContext = itemUsageContext;
        this.duration = duration;
        this.playerPositionReference = playerPositionReference;
    }

    public float getProgress() {
        float value = (float) passedTicks / duration;

        if (value > 1) {
            return 1;
        }

        return value;
    }

    public void tick() {
        passedTicks++;

        if (passedTicks >= duration) {
            isDone = true;
        }
    }

    public boolean didPositionChangedTooMuch(Player player) {
        double yawRad = Math.toRadians(-player.getYRot());
        double pitchRad = Math.toRadians(player.getXRot());

        Vec3 direction = new Vec3(
                Math.sin(yawRad) * Math.cos(pitchRad),
                -Math.sin(pitchRad),
                Math.cos(yawRad) * Math.cos(pitchRad)
        );

        Vec3 cameraPosition = player.position().add(
                0,
                player.getStandingEyeHeight(
                    player.getPose(),
                    player.getDimensions(player.getPose())
                ),
                0
        );

        // The default raycast method of player entity is not used here because it is less accurate
        BlockHitResult hitResult = itemUsageContext.getLevel().clip(new ClipContext(
                cameraPosition,
                cameraPosition.add(direction.scale(player.getBlockReach())),
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.ANY,
                player
        ));

        if (hitResult.getType() == BlockHitResult.Type.MISS) {
            return true;
        }

        if (!hitResult.getBlockPos().equals(itemUsageContext.getClickedPos())) {
            return true;
        }

        return hitResult.getDirection() != itemUsageContext.getClickedFace();
    }
}