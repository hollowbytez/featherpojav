/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.animation.AnimationTicker
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.animation;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;

public class AnimationTicker
implements ITickListener {
    private final AnimationData data;

    public AnimationTicker(AnimationData data) {
        this.data = data;
    }

    public void updateTick(int tick) {
        this.data.tick += 1.0;
    }
}

