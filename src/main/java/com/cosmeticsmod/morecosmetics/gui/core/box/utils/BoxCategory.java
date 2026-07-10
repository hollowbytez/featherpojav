/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.GuiComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory
 */
package com.cosmeticsmod.morecosmetics.gui.core.box.utils;

import com.cosmeticsmod.morecosmetics.gui.core.GuiComponent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BoxCategory {
    private String name;
    private String iconPath;
    private String title;
    private boolean listCategory;
    private ArrayList<GuiComponent> entries = new ArrayList();
    private Runnable callback;

    public BoxCategory(String name, String iconPath, ArrayList<GuiComponent> entries, int selectedBox) {
        this.name = name;
        this.iconPath = iconPath;
        this.entries = entries;
    }

    public BoxCategory(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public BoxCategory fillEntries(Consumer<ArrayList<GuiComponent>> fillCall, boolean listCategory) {
        fillCall.accept(this.entries);
        this.listCategory = listCategory;
        return this;
    }

    public BoxCategory setCallback(Runnable callback) {
        this.callback = callback;
        return this;
    }

    public boolean isListCategory() {
        return this.listCategory;
    }

    public boolean hasCallback() {
        return this.callback != null;
    }

    public void runCallback() {
        this.callback.run();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<GuiComponent> getEntries() {
        return this.entries;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public BoxCategory setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return this.title == null ? this.name : this.title;
    }
}

