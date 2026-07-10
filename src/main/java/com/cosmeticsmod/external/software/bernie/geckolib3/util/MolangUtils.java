/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.MolangUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.util;

public class MolangUtils {
    public static float normalizeTime(long timestamp) {
        return (float)timestamp / 24000.0f;
    }

    public static float booleanToFloat(boolean input) {
        return input ? 1.0f : 0.0f;
    }
}

