/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.AnimationUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.util;

public class AnimationUtils {
    public static double convertTicksToSeconds(double ticks) {
        return ticks / 20.0;
    }

    public static double convertSecondsToTicks(double seconds) {
        return seconds * 20.0;
    }
}

