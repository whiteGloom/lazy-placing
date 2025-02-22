package dev.emiller.mc.lazyplacing.lib;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import static net.minecraft.server.network.ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE;


public class LazyPlacingContext {
    public int passedTicks = 0;
    public BlockItem originalBlockReference;
    public ItemUsageContext itemUsageContext;
    public Vec3d playerPositionReference;
    public int duration;
    public boolean isDone = false;

    public LazyPlacingContext(
            BlockItem originalBlockReference, ItemUsageContext itemUsageContext, int duration,
            Vec3d playerPositionReference
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

    public boolean didPositionChangedTooMuch(PlayerEntity player) {
        double yawRad = Math.toRadians(-player.getYaw());
        double pitchRad = Math.toRadians(player.getPitch());

        Vec3d direction = new Vec3d(
                Math.sin(yawRad) * Math.cos(pitchRad),
                -Math.sin(pitchRad),
                Math.cos(yawRad) * Math.cos(pitchRad)
        );

        Vec3d cameraPosition = player.getPos().add(0, player.getStandingEyeHeight(), 0);

        // Default raycast method of player entity is not used here because it is less accurate
        BlockHitResult hitResult = itemUsageContext.getWorld().raycast(new RaycastContext(
                cameraPosition,
                cameraPosition.add(direction.multiply(Math.sqrt(MAX_BREAK_SQUARED_DISTANCE))),
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.ANY,
                player
        ));

        if (hitResult.getType() == BlockHitResult.Type.MISS) {
            return true;
        }

        if (!hitResult.getBlockPos().equals(itemUsageContext.getBlockPos())) {
            return true;
        }

        return hitResult.getSide() != itemUsageContext.getSide();
    }
}