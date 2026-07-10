/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 */
package com.cosmeticsmod.morecosmetics.utils;

public class MathUtils {
    private static final float[] SIN_TABLE = new float[4096];

    public static float clampFloat(float num, float min, float max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static int clampInt(int num, int min, int max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static float sin(float value) {
        return SIN_TABLE[(int)(value * 651.8986f) & 0xFFF];
    }

    public static float cos(float value) {
        return SIN_TABLE[(int)(value * 651.8986f + 1024.0f) & 0xFFF];
    }

    public static double easeOutBack(double x) {
        double c1 = 1.70158;
        double c3 = c1 + 1.0;
        return 1.0 + c3 * Math.pow(x - 1.0, 3.0) + c1 * Math.pow(x - 1.0, 2.0);
    }

    public static double easeInBack(double x) {
        return 1.0 - Math.cos(x * Math.PI / 2.0);
    }

    public static int floorDouble(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }

    public static float floorDoubleToFloat(double value) {
        float i = (float)value;
        return value < (double)i ? i - 1.0f : i;
    }

    public static int absInt(int value) {
        return value >= 0 ? value : -value;
    }

    static {
        for (int i = 0; i < 4096; ++i) {
            MathUtils.SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 4096.0);
        }
    }
}

