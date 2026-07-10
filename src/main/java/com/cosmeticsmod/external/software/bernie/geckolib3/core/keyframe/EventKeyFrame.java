/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

public class EventKeyFrame<T> {
    private T eventData;
    private Double startTick;

    public EventKeyFrame(Double startTick, T eventData) {
        this.startTick = startTick;
        this.eventData = eventData;
    }

    public T getEventData() {
        return (T)this.eventData;
    }

    public Double getStartTick() {
        return this.startTick;
    }
}

