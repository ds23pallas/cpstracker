package com.ds23pallas.cpstracker;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.LogicalSide;
import org.slf4j.Logger;


public class CPSTrackerEventHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static CPSTrackerEventHandler cpsTrackerEventHandler;

    private int row1 = 20;
    private int row2 = 42;
    private int row3 = 64;
    private int col1 = 10;
    private int col2 = 40;
    private int col3 = 80;

    int onColor = 0xFFFFFF;
    int offColor = 0xc0c0c0;
    int fillColor = 0x80A0A0A0;
    private boolean aIsDown, wIsDown, sIsDown, dIsDown = false;


    CPSTrackerEventHandler() {
        LOGGER.info("In key handler, registering events");
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onTick);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onRenderGUI);
    }

    public static void init() {
        cpsTrackerEventHandler = new CPSTrackerEventHandler();
    }

    private void onRenderGUI(RenderGameOverlayEvent e) {
        PoseStack matrix = e.getMatrixStack();
        drawString(matrix,"W",col2,row1,(wIsDown?onColor:offColor));
        drawString(matrix,"A",col1,row2,(aIsDown?onColor:offColor));
        drawString(matrix,"S",col2,row2,(sIsDown?onColor:offColor));
        drawString(matrix,"D",col3,row2,(dIsDown?onColor:offColor));

        drawString(matrix,"LMB",col1,row3,offColor);
        drawString(matrix,"RMB",col3,row3,offColor);

    }

    private void onTick(TickEvent.ClientTickEvent e) {
        if (e.side == LogicalSide.CLIENT && e.phase == TickEvent.Phase.END) {
            aIsDown = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_A);
            sIsDown = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_S);
            dIsDown = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_D);
            wIsDown = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_W);
        }
    }

    private void drawString(PoseStack matrix, String text, int x, int y, int color) {
        Minecraft.getInstance().font.draw(matrix,text,x+7,y+7,color);
        GuiComponent.fill(matrix,x,y,x+20,y+20,fillColor);
    }

}
