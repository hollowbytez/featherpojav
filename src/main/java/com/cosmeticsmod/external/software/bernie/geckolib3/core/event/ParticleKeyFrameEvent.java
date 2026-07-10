/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.ParticleKeyFrameEvent
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.event;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent;

public class ParticleKeyFrameEvent<T>
extends KeyframeEvent<T> {
    public final String effect;
    public final String locator;
    public final String script;

    public ParticleKeyFrameEvent(T entity, double animationTick, String effect, String locator, String script, AnimationController controller) {
        super(entity, animationTick, controller);
        this.effect = effect;
        this.locator = locator;
        this.script = script;
    }
}

