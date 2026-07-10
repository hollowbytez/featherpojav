/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.RainbowHandler
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import java.awt.Color;

public class RainbowHandler
implements ITickListener {
    private static final int SIZE = 360;
    private static final int[] VALUES = new int[360];
    public static final int RAINBOW_ID = 1;
    public static int RAINBOW_VALUE;

    public RainbowHandler() {
        for (int i = 0; i < 360; ++i) {
            RainbowHandler.VALUES[i] = Color.HSBtoRGB((float)i / 360.0f, 1.0f, 1.0f);
        }
    }

    public void updateTick(int tick) {
        RAINBOW_VALUE = VALUES[tick * ModConfig.getConfig().rainbowSpeed % 360];
    }
}

