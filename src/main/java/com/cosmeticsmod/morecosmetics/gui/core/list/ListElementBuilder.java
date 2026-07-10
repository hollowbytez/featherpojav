/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 */
package com.cosmeticsmod.morecosmetics.gui.core.list;

import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategory;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry;
import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ListElementBuilder {
    public abstract ListComponent getSwitchElement(String var1, boolean var2, boolean var3, Consumer<Boolean> var4);

    public abstract ListComponent getButtonElement(String var1, Runnable var2);

    public abstract ListComponent getButtonElement(String var1, String var2, Runnable var3);

    public abstract ListComponent getTextBoxElement(String var1, String var2, Consumer<String> var3, Consumer<String> var4, Consumer<String> var5, Consumer<String> var6, int var7);

    public ListComponent getTextBoxElement(String title, String currentText, Consumer<String> confirmCallback, Consumer<String> delayedCallback, Consumer<String> liveCallback) {
        return this.getTextBoxElement(title, currentText, confirmCallback, delayedCallback, liveCallback, null, 300);
    }

    public ListComponent getSliderElement(String title, int min, int max, int current, Consumer<Integer> callback) {
        return this.getSliderElement(title, min, max, current, callback, 70, true);
    }

    public ListComponent getSelectiveSliderElement(String title, int min, int max, int current, Consumer<Integer> callback) {
        return this.getSelectiveSliderElement(title, min, max, current, callback, 70, true);
    }

    public abstract ListComponent getSliderElement(String var1, int var2, int var3, int var4, Consumer<Integer> var5, int var6, boolean var7);

    public abstract ListComponent getSelectiveSliderElement(String var1, int var2, int var3, int var4, Consumer<Integer> var5, int var6, boolean var7);

    public abstract ListComponent getColorPickerElement(String var1, int var2, int var3, boolean var4, Consumer<Integer> var5);

    public abstract ListComponent getNumberBoxElement(String var1, int var2, int var3, int var4, Consumer<Integer> var5);

    public ListComponent getSeparationElement(String text) {
        return this.getSeparationElement(text, 0);
    }

    public abstract ListComponent getSeparationElement(String var1, int var2);

    public abstract ListComponent getTextElement(String var1, String var2);

    public abstract ListComponent getListElement(String var1, List<String> var2, int var3, Consumer<String> var4);

    public abstract ListComponent getListElement(String var1, Map<String, FontImage> var2, String var3, int var4, Consumer<String> var5);

    public abstract ListComponent getTextureElement(String var1, ArrayList<TextureCategory> var2, int var3, int var4, Consumer<TextureEntry> var5);

    public abstract ListComponent getKeyBoxElement(String var1, int var2, Consumer<Integer> var3);
}

