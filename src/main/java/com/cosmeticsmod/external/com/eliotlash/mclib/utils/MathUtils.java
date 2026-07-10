/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.utils.MathUtils
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.utils;

public class MathUtils {
    public static int clamp(int x, int min, int max) {
        return x < min ? min : (x > max ? max : x);
    }

    public static float clamp(float x, float min, float max) {
        return x < min ? min : (x > max ? max : x);
    }

    public static double clamp(double x, double min, double max) {
        return x < min ? min : (x > max ? max : x);
    }

    public static int cycler(int x, int min, int max) {
        return x < min ? max : (x > max ? min : x);
    }

    public static float cycler(float x, float min, float max) {
        return x < min ? max : (x > max ? min : x);
    }

    public static double cycler(double x, double min, double max) {
        return x < min ? max : (x > max ? min : x);
    }
}

