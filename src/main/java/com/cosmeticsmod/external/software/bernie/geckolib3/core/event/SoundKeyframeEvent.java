/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.SoundKeyframeEvent
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.event;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent;

public class SoundKeyframeEvent<T>
extends KeyframeEvent<T> {
    public final String sound;

    public SoundKeyframeEvent(T entity, double animationTick, String sound, AnimationController controller) {
        super(entity, animationTick, controller);
        this.sound = sound;
    }
}

