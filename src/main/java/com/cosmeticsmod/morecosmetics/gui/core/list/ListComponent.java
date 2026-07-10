/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 */
package com.cosmeticsmod.morecosmetics.gui.core.list;

import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;

public abstract class ListComponent
extends GuiComponent {
    protected boolean childVisible;
    protected boolean lineVisible;
    protected String description;

    public ListComponent(String title, boolean enabled) {
        super(title, enabled);
    }

    public ListComponent(String title) {
        super(title);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isChildVisible()) {
            for (ListComponent tileComponent : this.childComponents) {
                tileComponent.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        if (this.mouseOver && this.parentBoxManager != null) {
            this.parentBoxManager.setExtensionVisible(mouseButton == 1, (GuiComponent)this);
        }
    }

    public void mouseRelease(int mouseX, int mouseY, int state) {
        if (this.isChildVisible()) {
            for (ListComponent tileComponent : this.childComponents) {
                tileComponent.mouseRelease(mouseX, mouseY, state);
            }
        }
    }

    public void keyTyped(char charCode, int keyCode) {
        if (this.isChildVisible()) {
            for (ListComponent tileComponent : this.childComponents) {
                tileComponent.keyTyped(charCode, keyCode);
            }
        }
    }

    protected void setTileEnabled(boolean state) {
        this.enabled = state;
        if (this.childVisible && !this.enabled) {
            this.childVisible = false;
        }
    }

    public boolean isChildVisible() {
        return this.childVisible;
    }

    public boolean hasEnabledTile() {
        return this.hasChildComponents() && this.enabled;
    }

    public ListComponent setDescription(String description) {
        this.description = description;
        return this;
    }

    public ListComponent setLineVisible(boolean lineVisible) {
        this.lineVisible = lineVisible;
        return this;
    }

    public boolean isLineVisible() {
        return this.lineVisible;
    }

    public int getControlWidth() {
        return 40;
    }
}

