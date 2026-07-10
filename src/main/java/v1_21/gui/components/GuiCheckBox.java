/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.GuiCheckBox
 */
package v1_21.morecosmetics.gui.components;

import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import v1_21.morecosmetics.DrawUtils;

@Environment(value=EnvType.CLIENT)
public class GuiCheckBox {
    private static final Identifier CROSS = Identifier.of((String)"morecosmetics/gui/icons/close.png");
    private Color boxColor;
    private boolean selected;
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private boolean enabled = true;

    public GuiCheckBox(int x, int y, int boxWidth, int boxHeight, Color boxColor) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = boxWidth;
        this.height = boxHeight;
        this.boxColor = boxColor;
    }

    public void drawCheckBox(int mouseX, int mouseY) {
        DrawUtils.drawRoundedRect((int)this.xPosition, (int)this.yPosition, (int)(this.xPosition + this.width), (int)(this.yPosition + this.height), (int)(this.enabled ? UIConstants.UI_SEPARATION_COLOR : this.getTransparentBoxColor(this.boxColor)));
        DrawUtils.drawRoundedRect((int)(this.xPosition + 1), (int)(this.yPosition + 1), (int)(this.xPosition + this.width - 1), (int)(this.yPosition + this.height - 1), (int)(this.enabled ? UIConstants.UI_COMPONENT_COLOR : this.getTransparentBoxColor(new Color(UIConstants.UI_COMPONENT_COLOR))));
        if (this.selected) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(CROSS);
            DrawUtils.drawTexture((float)(this.xPosition + 2), (float)(this.yPosition + this.height / 2 - 5), (float)256.0f, (float)256.0f, (float)10.0f, (float)10.0f, (float)0.99f, (int)-1, (Identifier)CROSS);
        }
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        boolean pressed;
        boolean bl = pressed = this.enabled && this.isHovered(mouseX, mouseY);
        if (pressed) {
            this.selected = !this.selected;
        }
        return pressed;
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    private int getTransparentBoxColor(Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), 100).getRGB();
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

