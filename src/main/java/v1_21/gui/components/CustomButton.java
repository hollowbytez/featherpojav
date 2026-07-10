/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.CustomButton
 */
package v1_21.morecosmetics.gui.components;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.DrawUtils;

@Environment(value=EnvType.CLIENT)
public class CustomButton {
    public int xPosition;
    public int yPosition;
    private int width;
    private int height;
    private float size = 1.0f;
    private int offset;
    private String text;
    private boolean enabled = true;

    public CustomButton(int x, int y, String buttonText) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = 200;
        this.height = 20;
        this.text = buttonText;
    }

    public CustomButton(int x, int y, int width, int height, String buttonText) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.text = buttonText;
    }

    public void drawButton(int mouseX, int mouseY) {
        DrawUtils.drawRoundedRect((int)this.xPosition, (int)this.yPosition, (int)(this.xPosition + this.width), (int)(this.yPosition + this.height), (int)UIConstants.UI_SEPARATION_COLOR);
        DrawUtils.drawRoundedRect((int)(this.xPosition + 1), (int)(this.yPosition + 1), (int)(this.xPosition + this.width - 1), (int)(this.yPosition + this.height - 1), (int)UIConstants.UI_COMPONENT_COLOR);
        if (this.isHovered(mouseX, mouseY)) {
            DrawUtils.drawRoundedRect((int)(this.xPosition + 1), (int)(this.yPosition + 1), (int)(this.xPosition + this.width - 1), (int)(this.yPosition + this.height - 1), (int)UIConstants.UI_COMPONENT_HOVER);
        }
        if (this.size == 1.0f) {
            DrawUtils.drawCenteredString((String)this.text, (float)(this.xPosition + this.width / 2 + this.offset), (float)(this.yPosition + (this.height - 8) / 2));
        } else {
            DrawUtils.drawCenteredString((String)this.text, (float)(this.xPosition + this.width / 2 + this.offset), (float)(this.yPosition + (this.height - 8) / 2), (float)this.size);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public CustomButton setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public CustomButton setSize(float size) {
        this.size = size;
        return this;
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        boolean hovered;
        boolean bl = hovered = this.enabled && this.isHovered(mouseX, mouseY);
        if (hovered) {
            MoreCosmetics.getInstance().getVersionAdapter().playButtonSound();
        }
        return hovered;
    }
}

