package com.khofonyx.encumbered.client;

import com.khofonyx.encumbered.common.events.PlayerWeightHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class WeightOverlay {

    public static void renderWeight(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            float weight = PlayerWeightHandler.calculateWeight(player);

            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();

            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            String weightText = "Weight: " + weight;
            int textWidth = mc.font.width(weightText);

            guiGraphics.drawString(mc.font, Component.literal(weightText),
                    (screenWidth - textWidth) / 2, screenHeight / 2, 0xFFFFFF);

            poseStack.popPose();
        }
    }
}

