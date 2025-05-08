package dev.emiller.mc.lazyplacing;

import dev.emiller.mc.lazyplacing.configs.LazyPlacingConfigs;
import dev.emiller.mc.lazyplacing.network.LazyPlacingNetwork;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(LazyPlacing.MODID)
public class LazyPlacing
{
    public static final String MODID = "lazyplacing";

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event)
        {
            LazyPlacingConfigs.clearHostConfig();
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents
    {
        @SubscribeEvent
        public static void onServerStaring(ServerStartingEvent event)
        {
            LazyPlacingConfigs.loadServerConfig();
        }

        @SubscribeEvent
        public static void onServerStopping(ServerStoppedEvent event)
        {
            LazyPlacingConfigs.clearServerConfig();
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents
    {
        @SubscribeEvent
        public static void onModInitialization(FMLCommonSetupEvent event)
        {
            LazyPlacingNetwork.register();
        }
    }
}
