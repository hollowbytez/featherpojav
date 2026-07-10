/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.debug.EnumDebugState
 */
package com.cosmeticsmod.morecosmetics.utils.debug;

import java.awt.Color;

public enum EnumDebugState {
    LOG("[MoreCosmetics]", Color.WHITE),
    DEBUG("[Debug]", Color.LIGHT_GRAY),
    COMMAND("[CMD]", Color.WHITE),
    RESPONSE("[RESPONSE]", Color.WHITE),
    ERROR("[Error]", Color.RED);

    private Color textColor;
    private String prefix;

    private EnumDebugState(String prefix, Color textColor) {
        this.textColor = textColor;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Color getTextColor() {
        return this.textColor;
    }
}

