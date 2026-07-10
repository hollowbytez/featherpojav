/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.animated.AnimationModelEvent
 */
package com.cosmeticsmod.morecosmetics.models.animated;

public class AnimationModelEvent {
    protected String name;
    protected int priority;
    protected boolean triggerNew;
    protected boolean triggered;

    public AnimationModelEvent(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public boolean isTriggered(Object ... parameter) {
        if (this.triggerNew) {
            this.triggerNew = false;
        }
        return this.wasTriggered();
    }

    public void reset() {
        this.triggered = false;
        this.triggerNew = false;
    }

    protected void trigger(boolean state) {
        if (!this.triggered && state) {
            this.triggerNew = true;
        } else if (state && this.triggerNew) {
            this.triggerNew = false;
        }
        this.triggered = state;
    }

    public boolean isTriggerActive() {
        return this.triggered;
    }

    public boolean wasTriggered() {
        return this.triggerNew;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getName() {
        return this.name;
    }
}

