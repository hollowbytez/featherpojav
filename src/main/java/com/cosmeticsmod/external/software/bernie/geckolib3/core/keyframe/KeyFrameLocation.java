/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrameLocation
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame;

public class KeyFrameLocation<T extends KeyFrame> {
    public T currentFrame;
    public double currentTick;

    public KeyFrameLocation(T currentFrame, double currentTick) {
        this.currentFrame = currentFrame;
        this.currentTick = currentTick;
    }
}

