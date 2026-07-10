/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 */
package com.cosmeticsmod.morecosmetics.gui.core;

import com.cosmeticsmod.morecosmetics.gui.core.box.BoxManager;
import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class GuiComponent {
    protected boolean mouseOver;
    protected boolean enabled;
    protected int x;
    protected int y;
    protected int type;
    protected int componentIndex;
    protected String title;
    protected ArrayList<ListComponent> childComponents = new ArrayList();
    protected BoxManager parentBoxManager;
    protected Runnable discoverCallback;
    private boolean discovered;

    public GuiComponent(String title) {
        this.title = title;
    }

    public GuiComponent(String title, boolean enabled) {
        this.title = title;
        this.enabled = enabled;
    }

    public GuiComponent fillChild(Consumer<ArrayList<ListComponent>> call) {
        call.accept(this.childComponents);
        return this;
    }

    public void setComponentInfo(BoxManager parentBoxManager, int categoryIndex) {
        this.parentBoxManager = parentBoxManager;
        this.componentIndex = categoryIndex;
    }

    public GuiComponent onDiscover(Runnable discoverCallback) {
        this.discoverCallback = discoverCallback;
        return this;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public void keyTyped(char charCode, int keyCode) {
    }

    public void drawComponent(Object stack, int x, int y, int compWidth, int compHeight, int mouseX, int mouseY) {
        this.mouseOver = mouseX > x && mouseX < x + compWidth && mouseY > y && mouseY < y + compHeight;
        this.x = x;
        this.y = y;
        if (!this.discovered && this.discoverCallback != null) {
            this.discovered = true;
            this.discoverCallback.run();
        }
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean hasChildComponents() {
        return !this.childComponents.isEmpty();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<ListComponent> getChildComponents() {
        return this.childComponents;
    }

    public void update(Object value) {
    }

    public int getComponentIndex() {
        return this.componentIndex;
    }

    public int getType() {
        return this.type;
    }

    public boolean isDiscovered() {
        return this.discovered;
    }
}

