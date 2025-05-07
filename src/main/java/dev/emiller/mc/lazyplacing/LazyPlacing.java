package dev.emiller.mc.lazyplacing;

import com.mojang.logging.LogUtils;
import dev.emiller.mc.lazyplacing.libs.LazyPlacingConfig;
import dev.emiller.mc.lazyplacing.network.LazyPlacingNetwork;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LazyPlacing.MODID)
public class LazyPlacing
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "lazyplacing";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final LazyPlacingConfig CONFIG = LazyPlacingConfig.load();

    public LazyPlacing(FMLJavaModLoadingContext context)
    {
        // Register ourselves for server and other game events we are interested in
        context.getModEventBus().register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLCommonSetupEvent event)
    {
        LazyPlacingNetwork.register();
    }
}
