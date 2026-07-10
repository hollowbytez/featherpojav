/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiListener
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager$BoxGuiInstance
 */
package com.cosmeticsmod.morecosmetics.gui.core;

import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;

public interface GuiListener {
    default public void initGui(BoxManager.BoxGuiInstance instance) {
    }

    default public void drawScreen(Object stack, int mouseX, int mouseY, boolean enabled) {
    }

    default public boolean mouseClicked(BoxManager.BoxGuiInstance instance, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    default public void keyTyped(char charCode, int keyCode) {
    }
}

