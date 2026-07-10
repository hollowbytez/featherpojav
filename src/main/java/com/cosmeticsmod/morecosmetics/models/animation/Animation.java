/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.animation.Animation
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationType
 */
package com.cosmeticsmod.morecosmetics.models.animation;

import com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationType;

public class Animation {
    private final AnimationType type;
    private AnimationAxis axis;
    private float multiplier = 1.0f;

    public Animation(AnimationType type) {
        this.type = type;
        this.axis = type.getDefaultAxis();
    }

    public AnimationType getType() {
        return this.type;
    }

    public AnimationAxis getAxis() {
        return this.axis;
    }

    public void setAxis(AnimationAxis axis) {
        this.axis = axis;
    }

    public float getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}

