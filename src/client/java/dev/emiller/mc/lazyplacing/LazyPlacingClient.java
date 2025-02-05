package dev.emiller.mc.lazyplacing;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class LazyPlacingClient implements ClientModInitializer {
    static public float placingValue = 0.0f;

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClientPlayNetworking.registerGlobalReceiver(
                LazyPlacingConstants.BLOCK_PLACING_PROGRESS_UPDATE_PACKET_ID,
                (client, handler, buf, responseSender) -> placingValue = buf.readFloat()
        );
    }
}