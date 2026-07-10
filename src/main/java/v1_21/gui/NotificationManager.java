/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.AnimationState
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.Notification
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.math.MatrixStack
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.NotificationManager
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.gui;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.gui.core.notification.AnimationState;
import com.cosmeticsmod.morecosmetics.gui.core.notification.Notification;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
public class NotificationManager
extends NotificationHandler {
    private static CustomTextureManager textureManager = CustomTextureManager.getGlobalInstance();

    public void draw(Object stack, int width) {
        if (notifications.isEmpty()) {
            return;
        }
        if (width == -1) {
            width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        }
        this.drawNotificationTopRight(stack, width);
    }

    private void drawNotificationTopRight(Object oldStack, int width) {
        int currentHeight = 24;
        MatrixStack stack = StackHolder.STACK;
        for (int i = 1; i >= 0; --i) {
            int animationMove;
            if (notifications.size() <= i) continue;
            Notification notification = (Notification)notifications.get(i);
            if (notification.getInitTime() == 0L) {
                notification.setInitTime();
                notification.setAnimationState(AnimationState.IN);
            }
            int n = animationMove = notification.getAnimationState() == AnimationState.NONE ? 0 : (int)Math.round(MathUtils.easeInBack((double)notification.getAnimationIndex()) * 100.0) - 200;
            if (notification.getAnimationState() != AnimationState.NONE) {
                notification.addAnimationsIndex();
                if (notification.getAnimationState() == AnimationState.IN && notification.getAnimationIndex() >= 2.0) {
                    notification.setAnimationState(AnimationState.NONE);
                }
                if (notification.getAnimationState() == AnimationState.OUT && notification.getAnimationIndex() >= 4.0) {
                    notification.setAnimationState(AnimationState.NONE);
                }
            }
            stack.push();
            stack.translate(0.0f, 0.0f, 200.0f);
            DrawUtils.drawRect((int)(width - this.getWholeWidth() - animationMove), (int)currentHeight, (int)(width - animationMove), (int)(currentHeight + this.getWholeHeight(notification.getLines().length)), (int)UIConstants.UI_COMPONENT_COLOR);
            DrawUtils.drawRect((int)(width - this.getWholeWidth() - 1 - animationMove), (int)currentHeight, (int)(width - this.getWholeWidth() - animationMove), (int)(currentHeight + this.getWholeHeight(notification.getLines().length)), (int)notification.getRemainingColor());
            float timeRemainPercentage = (float)(System.currentTimeMillis() - notification.getInitTime()) / 4000.0f;
            timeRemainPercentage = Math.min(timeRemainPercentage, 1.0f);
            int drawDuration = (int)(timeRemainPercentage * (float)this.getWholeHeight(notification.getLines().length));
            DrawUtils.drawRect((int)(width - this.getWholeWidth() - 1 - animationMove), (int)(currentHeight + this.getWholeHeight(notification.getLines().length)), (int)(width - this.getWholeWidth() - animationMove), (int)(currentHeight + drawDuration), (int)notification.getProgressColor());
            DrawUtils.drawString((String)notification.getTitle(), (float)(width - this.getWholeWidth() + 7 + 16 + 7 - animationMove), (float)(currentHeight + 7));
            for (int j = 0; j < notification.getLines().length; ++j) {
                String line = notification.getLines()[j];
                DrawUtils.drawString((String)line, (float)(width - this.getWholeWidth() + 7 + 16 + 7 - animationMove), (float)(currentHeight + 7 + 5 + DrawUtils.getFontHeight() * (j + 1)));
            }
            if (notification.getLogo() != null) {
                Identifier id = textureManager.getResource("notification-" + notification.getTitle(), notification.getLogo());
                MinecraftClient.getInstance().getTextureManager().bindTexture(id);
                DrawUtils.drawTexture((float)(width - this.getWholeWidth() + 7 - animationMove), (float)(currentHeight + 7), (float)256.0f, (float)256.0f, (float)16.0f, (float)16.0f, (float)1.0f, (int)UIConstants.TEXURE_COLOR, (Identifier)id);
            }
            stack.pop();
            if (System.currentTimeMillis() - notification.getInitTime() > 4000L && notification.getAnimationState() == AnimationState.NONE && notification.getAnimationIndex() < 4.0) {
                notification.setAnimationState(AnimationState.OUT);
            }
            if (System.currentTimeMillis() - notification.getInitTime() > 4000L && notification.getAnimationState() == AnimationState.NONE) {
                notifications.remove(notification);
            }
            currentHeight += 4 + this.getWholeHeight(notification.getLines().length);
        }
    }

    public int getStringWidth(String text) {
        return DrawUtils.getStringWidth((String)text);
    }

    public int getWholeHeight(int lineLength) {
        return 19 + DrawUtils.getFontHeight() * (lineLength + 1);
    }
}

