package com.ds23pallas.cpstracker;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
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
    public static ClickTracker clickTracker;

    public CPSTracker() {
        clickTracker = new ClickTracker();
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::clientSetup);
        //Mouseclick tracker is bound outside of main event handler
        MinecraftForge.EVENT_BUS.register(this);
    }

    //Bind the keyboard tracker
    private void clientSetup(FMLClientSetupEvent event) {
        CPSTrackerEventHandler.init();
    }

    @SubscribeEvent
    public void playerEvent(PlayerInteractEvent e) {
        //TODO - fix clicking on blocks
        if(e.getClass()==PlayerInteractEvent.LeftClickEmpty.class) {
            clickTracker.addClick();
        }
    }

}
