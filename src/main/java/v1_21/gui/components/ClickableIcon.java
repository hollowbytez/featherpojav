/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.gui.components.ClickableIcon
 *  v1_21.morecosmetics.models.textures.CustomImage
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.gui.components;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.models.textures.CustomImage;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
public class ClickableIcon {
    private static CustomTextureManager textureManager = CustomTextureManager.getGlobalInstance();
    public int xPosition;
    public int yPosition;
    private int width;
    private int height;
    private String source;
    private boolean hover;
    private boolean online;
    private int hoverColor = -1;
    private int color = -1;
    private String hoverText;

    public ClickableIcon(int width, int height, String source, boolean online) {
        this(0, 0, width, height, source, online);
    }

    public ClickableIcon(int x, int y, int width, int height, String source, boolean online) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.source = source;
        this.online = online;
    }

    public void drawIcon(int mouseX, int mouseY) {
        this.drawIcon(this.xPosition, this.yPosition, mouseX, mouseY);
    }

    public void drawIcon(int x, int y, int mouseX, int mouseY) {
        CustomImage img;
        Identifier loc = null;
        loc = this.online ? ((img = textureManager.getImage(this.source, this.source, null)) != null ? img.getLocation() : null) : textureManager.getResource(this.source, this.source);
        if (loc != null) {
            this.hover = mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;
            MinecraftClient.getInstance().getTextureManager().bindTexture(loc);
            DrawUtils.drawTexture((float)x, (float)y, (float)256.0f, (float)256.0f, (float)this.width, (float)this.height, (float)0.99f, (int)(this.hover ? this.hoverColor : this.color), (Identifier)loc);
            if (this.hover && this.hoverText != null) {
                DrawUtils.drawToolTip((String)this.hoverText, (int)mouseX, (int)mouseY);
            }
        }
    }

    public void setSize(int size) {
        this.width = size;
        this.height = size;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setHoverText(String hoverText) {
        this.hoverText = hoverText;
    }

    public String getHoverText() {
        return this.hoverText;
    }

    public int getColor() {
        return this.color;
    }

    public void setHoverColor(int hoverColor) {
        this.hoverColor = hoverColor;
    }

    public int getHoverColor() {
        return this.hoverColor;
    }

    public boolean isHovered() {
        return this.hover;
    }
}

