/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.box.CustomBoxGui
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory
 */
package com.cosmeticsmod.morecosmetics.gui.core.box;

import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxCategory;
import java.util.List;

public interface CustomBoxGui {
    public void fillGui(List<BoxCategory> var1);

    default public void onGuiClosed() {
    }
}

