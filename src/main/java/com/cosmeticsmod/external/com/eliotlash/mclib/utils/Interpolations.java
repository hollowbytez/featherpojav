/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.utils.Interpolations
 *  com.cosmeticsmod.external.com.eliotlash.mclib.utils.MathHelper
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.utils;

import com.cosmeticsmod.external.com.eliotlash.mclib.utils.MathHelper;

/*
 * Exception performing whole class analysis ignored.
 */
public class Interpolations {
    public static float lerp(float a, float b, float position) {
        return a + (b - a) * position;
    }

    public static float lerpYaw(float a, float b, float position) {
        a = MathHelper.wrapDegrees((float)a);
        b = MathHelper.wrapDegrees((float)b);
        return Interpolations.lerp((float)a, (float)Interpolations.normalizeYaw((float)a, (float)b), (float)position);
    }

    public static double cubicHermite(double y0, double y1, double y2, double y3, double x) {
        double a = -0.5 * y0 + 1.5 * y1 - 1.5 * y2 + 0.5 * y3;
        double b = y0 - 2.5 * y1 + 2.0 * y2 - 0.5 * y3;
        double c = -0.5 * y0 + 0.5 * y2;
        return ((a * x + b) * x + c) * x + y1;
    }

    public static double cubicHermiteYaw(float y0, float y1, float y2, float y3, float position) {
        y0 = MathHelper.wrapDegrees((float)y0);
        y1 = MathHelper.wrapDegrees((float)y1);
        y2 = MathHelper.wrapDegrees((float)y2);
        y3 = MathHelper.wrapDegrees((float)y3);
        y1 = Interpolations.normalizeYaw((float)y0, (float)y1);
        y2 = Interpolations.normalizeYaw((float)y1, (float)y2);
        y3 = Interpolations.normalizeYaw((float)y2, (float)y3);
        return Interpolations.cubicHermite((double)y0, (double)y1, (double)y2, (double)y3, (double)position);
    }

    public static float cubic(float y0, float y1, float y2, float y3, float x) {
        float a = y3 - y2 - y0 + y1;
        float b = y0 - y1 - a;
        float c = y2 - y0;
        return ((a * x + b) * x + c) * x + y1;
    }

    public static float cubicYaw(float y0, float y1, float y2, float y3, float position) {
        y0 = MathHelper.wrapDegrees((float)y0);
        y1 = MathHelper.wrapDegrees((float)y1);
        y2 = MathHelper.wrapDegrees((float)y2);
        y3 = MathHelper.wrapDegrees((float)y3);
        y1 = Interpolations.normalizeYaw((float)y0, (float)y1);
        y2 = Interpolations.normalizeYaw((float)y1, (float)y2);
        y3 = Interpolations.normalizeYaw((float)y2, (float)y3);
        return Interpolations.cubic((float)y0, (float)y1, (float)y2, (float)y3, (float)position);
    }

    public static float bezierX(float x1, float x2, float t, float epsilon) {
        float x3 = t;
        float init = Interpolations.bezier((float)0.0f, (float)x1, (float)x2, (float)1.0f, (float)t);
        float factor = Math.copySign(0.1f, t - init);
        while (Math.abs(t - init) > epsilon) {
            float oldFactor = factor;
            init = Interpolations.bezier((float)0.0f, (float)x1, (float)x2, (float)1.0f, (float)(x3 += factor));
            if (Math.copySign(factor, t - init) == oldFactor) continue;
            factor *= -0.25f;
        }
        return x3;
    }

    public static float bezierX(float x1, float x2, float t) {
        return Interpolations.bezierX((float)x1, (float)x2, (float)t, (float)5.0E-4f);
    }

    public static float bezier(float x1, float x2, float x3, float x4, float t) {
        float t2 = Interpolations.lerp((float)x1, (float)x2, (float)t);
        float t3 = Interpolations.lerp((float)x2, (float)x3, (float)t);
        float t4 = Interpolations.lerp((float)x3, (float)x4, (float)t);
        float t5 = Interpolations.lerp((float)t2, (float)t3, (float)t);
        float t6 = Interpolations.lerp((float)t3, (float)t4, (float)t);
        return Interpolations.lerp((float)t5, (float)t6, (float)t);
    }

    public static float normalizeYaw(float a, float b) {
        float diff = a - b;
        if (diff > 180.0f || diff < -180.0f) {
            diff = Math.copySign(360.0f - Math.abs(diff), diff);
            return a + diff;
        }
        return b;
    }

    public static float envelope(float x, float duration, float fades) {
        return Interpolations.envelope((float)x, (float)0.0f, (float)fades, (float)(duration - fades), (float)duration);
    }

    public static float envelope(float x, float lowIn, float lowOut, float highIn, float highOut) {
        if (x < lowIn || x > highOut) {
            return 0.0f;
        }
        if (x < lowOut) {
            return (x - lowIn) / (lowOut - lowIn);
        }
        if (x > highIn) {
            return 1.0f - (x - highIn) / (highOut - highIn);
        }
        return 1.0f;
    }

    public static double lerp(double a, double b, double position) {
        return a + (b - a) * position;
    }

    public static double lerpYaw(double a, double b, double position) {
        a = MathHelper.wrapDegrees((double)a);
        b = MathHelper.wrapDegrees((double)b);
        return Interpolations.lerp((double)a, (double)Interpolations.normalizeYaw((double)a, (double)b), (double)position);
    }

    public static double cubic(double y0, double y1, double y2, double y3, double x) {
        double a = y3 - y2 - y0 + y1;
        double b = y0 - y1 - a;
        double c = y2 - y0;
        return ((a * x + b) * x + c) * x + y1;
    }

    public static double cubicYaw(double y0, double y1, double y2, double y3, double position) {
        y0 = MathHelper.wrapDegrees((double)y0);
        y1 = MathHelper.wrapDegrees((double)y1);
        y2 = MathHelper.wrapDegrees((double)y2);
        y3 = MathHelper.wrapDegrees((double)y3);
        y1 = Interpolations.normalizeYaw((double)y0, (double)y1);
        y2 = Interpolations.normalizeYaw((double)y1, (double)y2);
        y3 = Interpolations.normalizeYaw((double)y2, (double)y3);
        return Interpolations.cubic((double)y0, (double)y1, (double)y2, (double)y3, (double)position);
    }

    public static double bezierX(double x1, double x2, double t, double epsilon) {
        double x3 = t;
        double init = Interpolations.bezier((double)0.0, (double)x1, (double)x2, (double)1.0, (double)t);
        double factor = Math.copySign((double)0.1f, t - init);
        while (Math.abs(t - init) > epsilon) {
            double oldFactor = factor;
            init = Interpolations.bezier((double)0.0, (double)x1, (double)x2, (double)1.0, (double)(x3 += factor));
            if (Math.copySign(factor, t - init) == oldFactor) continue;
            factor *= -0.25;
        }
        return x3;
    }

    public static double bezierX(double x1, double x2, float t) {
        return Interpolations.bezierX((double)x1, (double)x2, (double)t, (double)5.0E-4f);
    }

    public static double bezier(double x1, double x2, double x3, double x4, double t) {
        double t2 = Interpolations.lerp((double)x1, (double)x2, (double)t);
        double t3 = Interpolations.lerp((double)x2, (double)x3, (double)t);
        double t4 = Interpolations.lerp((double)x3, (double)x4, (double)t);
        double t5 = Interpolations.lerp((double)t2, (double)t3, (double)t);
        double t6 = Interpolations.lerp((double)t3, (double)t4, (double)t);
        return Interpolations.lerp((double)t5, (double)t6, (double)t);
    }

    public static double normalizeYaw(double a, double b) {
        double diff = a - b;
        if (diff > 180.0 || diff < -180.0) {
            diff = Math.copySign(360.0 - Math.abs(diff), diff);
            return a + diff;
        }
        return b;
    }

    public static double envelope(double x, double duration, double fades) {
        return Interpolations.envelope((double)x, (double)0.0, (double)fades, (double)(duration - fades), (double)duration);
    }

    public static double envelope(double x, double lowIn, double lowOut, double highIn, double highOut) {
        if (x < lowIn || x > highOut) {
            return 0.0;
        }
        if (x < lowOut) {
            return (x - lowIn) / (lowOut - lowIn);
        }
        if (x > highIn) {
            return 1.0 - (x - highIn) / (highOut - highIn);
        }
        return 1.0;
    }
}

