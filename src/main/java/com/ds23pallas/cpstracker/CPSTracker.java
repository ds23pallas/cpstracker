package com.ds23pallas.cpstracker;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("cpstracker")
public class CPSTracker {
    private static final Logger LOGGER = LogUtils.getLogger();

    public CPSTracker() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CPS TRACKER CLIENT SETUP");
        CPSTrackerEventHandler.init();
    }

    @SubscribeEvent
    public void playerEvent(PlayerInteractEvent e) {
        if(e.getClass()==PlayerInteractEvent.LeftClickEmpty.class) {
            LOGGER.info("Clicked " + System.currentTimeMillis());
        }
    }

}
