/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.list.CustomListGui
 *  com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent
 */
package com.cosmeticsmod.morecosmetics.gui.core.list;

import com.cosmeticsmod.morecosmetics.gui.core.list.ListComponent;
import java.util.List;

public interface CustomListGui {
    public void fillGui(List<ListComponent> var1);

    default public void onGuiClosed() {
    }
}

