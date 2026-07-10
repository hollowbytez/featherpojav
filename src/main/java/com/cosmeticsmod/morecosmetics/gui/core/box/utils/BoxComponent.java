/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent
 */
package com.cosmeticsmod.morecosmetics.gui.core.box.utils;

import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;

public class BoxComponent
extends GuiComponent {
    protected int height;

    public BoxComponent(String title, boolean enabled) {
        super(title, enabled);
    }

    public BoxComponent(String title) {
        super(title);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public void keyTyped(char charCode, int keyCode) {
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        this.height = compHeight;
        super.drawComponent(stack, x, y, compWidth, compHeight, mouseX, mouseY);
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

