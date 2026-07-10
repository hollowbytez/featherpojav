/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.OpenMode
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.utils.ModConfig;

public enum OpenMode {
    ALL,
    BUTTON,
    KEYBIND;


    public static boolean openOnlyOn(OpenMode mode) {
        return ModConfig.getConfig().openMode == mode.ordinal();
    }
}

