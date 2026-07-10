/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.utils.Interpolation
 */
package com.cosmeticsmod.external.com.eliotlash.mclib.utils;

public enum Interpolation {
    LINEAR("linear") {
        public float interpolate(float a, float b, float t) {
            return a + (b - a) * t;
        }
    },
    QUAD_IN("quad_in") {
        public float interpolate(float a, float b, float t) {
            return a + (b - a) * t * t;
        }
    },
    QUAD_OUT("quad_out") {
        public float interpolate(float a, float b, float t) {
            return a - (b - a) * t * (t - 2);
        }
    },
    QUAD_INOUT("quad_inout") {
        public float interpolate(float a, float b, float t) {
            if ((t *= 2.0f) < 1.0f) return a + (b - a) / 2.0f * t * t;
            return a - (b - a) / 2.0f * ((t - 1.0f) * (t - 3.0f) - 1.0f);
        }
    },
    CUBIC_IN("cubic_in") {
        public float interpolate(float a, float b, float t) {
            return a + (b - a) * t * t * t;
        }
    },
    CUBIC_OUT("cubic_out") {
        public float interpolate(float a, float b, float t) {
            return a + (b - a) * ((t -= 1.0f) * t * t + 1.0f);
        }
    },
    CUBIC_INOUT("cubic_inout") {
        public float interpolate(float a, float b, float t) {
            if ((t *= 2.0f) < 1.0f) return a + (b - a) / 2.0f * t * t * t;
            return a + (b - a) / 2.0f * ((t -= 2.0f) * t * t + 2.0f);
        }
    },
    EXP_IN("exp_in") {
        public float interpolate(float a, float b, float t) {
            return t == 0.0f ? a : a + (b - a) * (float)Math.pow(2.0, 10.0f * (t - 1.0f));
        }
    },
    EXP_OUT("exp_out") {
        public float interpolate(float a, float b, float t) {
            return t == 1.0f ? b : a + (b - a) * (-(float)Math.pow(2.0, -10.0f * t) + 1.0f);
        }
    },
    EXP_INOUT("exp_inout") {
        public float interpolate(float a, float b, float t) {
            if (t == 0.0f) return a;
            if (t == 1.0f) return b;
            if ((t *= 2.0f) < 1.0f) return a + (b - a) / 2.0f * (float)Math.pow(2.0, 10.0f * (t - 1.0f));
            return a + (b - a) / 2.0f * (-(float)Math.pow(2.0, -10.0f * (t - 1.0f)) + 2.0f);
        }
    };

    public final String key;

    private Interpolation(String key) {
        this.key = key;
    }

    public abstract float interpolate(float var1, float var2, float var3);

    public String getName() {
        return "mclib.interpolations." + this.key;
    }
}

