/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame;

public class ParticleEventKeyFrame
extends EventKeyFrame<String> {
    public final String effect;
    public final String locator;
    public final String script;

    public ParticleEventKeyFrame(Double startTick, String effect, String locator, String script) {
        super(startTick, effect + "\n" + locator + "\n" + script);
        this.script = script;
        this.locator = locator;
        this.effect = effect;
    }

    public String getEventData() {
        return (String)super.getEventData();
    }
}

