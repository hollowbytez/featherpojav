/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame;

public class AnimationPoint {
    public final Double currentTick;
    public final Double animationEndTick;
    public final Double animationStartValue;
    public final Double animationEndValue;
    public final KeyFrame<IValue> keyframe;

    public AnimationPoint(KeyFrame<IValue> keyframe, Double currentTick, Double animationEndTick, Double animationStartValue, Double animationEndValue) {
        this.keyframe = keyframe;
        this.currentTick = currentTick;
        this.animationEndTick = animationEndTick;
        this.animationStartValue = animationStartValue;
        this.animationEndValue = animationEndValue;
    }

    public AnimationPoint(KeyFrame<IValue> keyframe, double tick, double animationEndTick, double animationStartValue, double animationEndValue) {
        this.keyframe = keyframe;
        this.currentTick = tick;
        this.animationEndTick = animationEndTick;
        this.animationStartValue = animationStartValue;
        this.animationEndValue = animationEndValue;
    }

    public String toString() {
        return "Tick: " + this.currentTick + " | End Tick: " + this.animationEndTick + " | Start Value: " + this.animationStartValue + " | End Value: " + this.animationEndValue;
    }
}

