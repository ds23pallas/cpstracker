package com.ds23pallas.cpstracker;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.Font;
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

    //positions of numbers
    private int row1 = 20;
    private int row2 = 40;
    private int row3 = 64;
    private int row4 = 86;
    private int row5 = 108;
    private int col1 = 10;
    private int col2 = 30;
    private int col3 = 50;

    //colors for rendering
    private int onColor = 0xea03ff;
    private int offColor = 0x27ff03;
    private int fillColor = 0x2803cdff;
    private boolean aIsDown, wIsDown, sIsDown, dIsDown, spaceIsDown = false;

    //caches of minecraft stuff
    private MouseHandler m = Minecraft.getInstance().mouseHandler;
    private Font f = Minecraft.getInstance().font;
    private long window = Minecraft.getInstance().getWindow().getWindow();

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
        drawString(matrix,"space",col1,row5,45,20,(spaceIsDown?onColor:offColor),true);

        drawString(matrix,"LMB",col1,row3,30,20,(m.isLeftPressed()?onColor:offColor),true);
        drawString(matrix,"RMB",col3,row3,30,20,(m.isRightPressed()?onColor:offColor),true);

        drawString(matrix, "CPS:" + Integer.toString(CPSTracker.leftClickTracker.getCount()),col1,row4,offColor,false);
        drawString(matrix, "CPSR:" + Integer.toString(CPSTracker.rightClickTracker.getCount()),col3,row4,offColor,false);

    }

    private void onTick(TickEvent.ClientTickEvent e) {
        if (e.side == LogicalSide.CLIENT) {
            aIsDown = InputConstants.isKeyDown(window, InputConstants.KEY_A);
            sIsDown = InputConstants.isKeyDown(window, InputConstants.KEY_S);
            dIsDown = InputConstants.isKeyDown(window, InputConstants.KEY_D);
            wIsDown = InputConstants.isKeyDown(window, InputConstants.KEY_W);
            spaceIsDown = InputConstants.isKeyDown(window, InputConstants.KEY_SPACE);
        }
    }

    //Draws the pressed key and box
    private void drawString(PoseStack matrix, String text, int x, int y, int color) {
        drawString(matrix,text,x,y,color,true);
    }

    private void drawString(PoseStack matrix, String text, int x, int y, int color, boolean drawBox) {
        drawString(matrix,text,x,y,20,20,color,drawBox);
    }

    private void drawString(PoseStack matrix, String text, int x, int y,int xOffset,int yOffset, int color, boolean drawBox) {
        f.draw(matrix,text,x+7,y+7,color);
        if(drawBox) GuiComponent.fill(matrix,x,y,x+xOffset,y+yOffset,fillColor);
    }
}
