/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.easing;

/*
 * Exception performing whole class analysis ignored.
 */
public enum EasingType {
    NONE,
    CUSTOM,
    Linear,
    Step,
    EaseInSine,
    EaseOutSine,
    EaseInOutSine,
    EaseInQuad,
    EaseOutQuad,
    EaseInOutQuad,
    EaseInCubic,
    EaseOutCubic,
    EaseInOutCubic,
    EaseInQuart,
    EaseOutQuart,
    EaseInOutQuart,
    EaseInQuint,
    EaseOutQuint,
    EaseInOutQuint,
    EaseInExpo,
    EaseOutExpo,
    EaseInOutExpo,
    EaseInCirc,
    EaseOutCirc,
    EaseInOutCirc,
    EaseInBack,
    EaseOutBack,
    EaseInOutBack,
    EaseInElastic,
    EaseOutElastic,
    EaseInOutElastic,
    EaseInBounce,
    EaseOutBounce,
    EaseInOutBounce;


    public static EasingType getEasingTypeFromString(String search) {
        for (EasingType each : EasingType.values()) {
            if (each.name().compareToIgnoreCase(search) != 0) continue;
            return each;
        }
        return null;
    }
}

