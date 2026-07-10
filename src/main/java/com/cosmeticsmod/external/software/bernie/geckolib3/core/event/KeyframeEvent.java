/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.event;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;

public abstract class KeyframeEvent<T> {
    private final T entity;
    private final double animationTick;
    private final AnimationController controller;

    public KeyframeEvent(T entity, double animationTick, AnimationController controller) {
        this.entity = entity;
        this.animationTick = animationTick;
        this.controller = controller;
    }

    public double getAnimationTick() {
        return this.animationTick;
    }

    public T getEntity() {
        return (T)this.entity;
    }

    public AnimationController getController() {
        return this.controller;
    }
}

