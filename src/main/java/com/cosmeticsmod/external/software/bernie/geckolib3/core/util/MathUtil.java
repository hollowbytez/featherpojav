/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingManager
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.util.MathUtil
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.util;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingManager;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint;
import java.util.function.Function;

/*
 * Exception performing whole class analysis ignored.
 */
public class MathUtil {
    public static float lerpValues(AnimationPoint animationPoint, EasingType easingType, Function<Double, Double> customEasingMethod) {
        if (animationPoint.currentTick >= animationPoint.animationEndTick) {
            return animationPoint.animationEndValue.floatValue();
        }
        if (animationPoint.currentTick == 0.0 && animationPoint.animationEndTick == 0.0) {
            return animationPoint.animationEndValue.floatValue();
        }
        if (easingType == EasingType.CUSTOM && customEasingMethod != null) {
            return MathUtil.lerpValues((double)customEasingMethod.apply(animationPoint.currentTick / animationPoint.animationEndTick), (double)animationPoint.animationStartValue, (double)animationPoint.animationEndValue);
        }
        if (easingType == EasingType.NONE && animationPoint.keyframe != null) {
            easingType = animationPoint.keyframe.easingType;
        }
        double ease = EasingManager.ease((double)(animationPoint.currentTick / animationPoint.animationEndTick), (EasingType)easingType, animationPoint.keyframe == null ? null : animationPoint.keyframe.easingArgs);
        return MathUtil.lerpValues((double)ease, (double)animationPoint.animationStartValue, (double)animationPoint.animationEndValue);
    }

    public static float lerpValues(double percentCompleted, double startValue, double endValue) {
        return (float)MathUtil.lerp((double)percentCompleted, (double)startValue, (double)endValue);
    }

    public static double lerp(double pct, double start, double end) {
        return start + pct * (end - start);
    }
}

