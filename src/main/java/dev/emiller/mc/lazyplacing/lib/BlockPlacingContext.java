package dev.emiller.mc.lazyplacing.lib;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import static net.minecraft.server.network.ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE;


public class BlockPlacingContext {
    int passedTicks = 0;
    BlockItem originalBlockReference;
    ItemUsageContext itemUsageContext;
    Vec3d playerPositionReference;
    int duration;
    private boolean _done = false;

    public BlockPlacingContext(
            BlockItem originalBlockReference, ItemUsageContext itemUsageContext, int duration,
            Vec3d playerPositionReference
    ) {
        this.originalBlockReference = originalBlockReference;
        this.itemUsageContext = itemUsageContext;
        this.duration = duration;
        this.playerPositionReference = playerPositionReference;
    }

    public float getProgress() {
        return (float) passedTicks / duration;
    }

    public boolean isDone() {
        return _done;
    }

    public void setDone() {
        originalBlockReference.place(new ItemPlacementContext(itemUsageContext));

        _done = true;
    }

    public void tick() {
        passedTicks++;

        if (passedTicks >= duration) {
            setDone();
        }
    }

    public boolean didPositionChangedTooMuch(ServerPlayerEntity player) {
        double yawRad = Math.toRadians(-player.getYaw());
        double pitchRad = Math.toRadians(player.getPitch());

        Vec3d direction = new Vec3d(
                Math.sin(yawRad) * Math.cos(pitchRad),
                -Math.sin(pitchRad),
                Math.cos(yawRad) * Math.cos(pitchRad)
        );

        Vec3d cameraPosition = player.getPos().add(0, player.getStandingEyeHeight(), 0);

        // Default raycast method of player entity is not used here because it is less accurate
        BlockHitResult hitResult = itemUsageContext.getWorld()
                .raycast(new RaycastContext(
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