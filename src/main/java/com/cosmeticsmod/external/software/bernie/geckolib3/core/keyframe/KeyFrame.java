/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KeyFrame<T> {
    private Double length;
    private T startValue;
    private T endValue;
    public EasingType easingType = EasingType.Linear;
    public List<Double> easingArgs = new ArrayList();

    public KeyFrame(Double length, T startValue, T endValue) {
        this.length = length;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public KeyFrame(Double length, T startValue, T endValue, EasingType easingType) {
        this.length = length;
        this.startValue = startValue;
        this.endValue = endValue;
        this.easingType = easingType;
    }

    public KeyFrame(Double length, T startValue, T endValue, EasingType easingType, List<Double> easingArgs) {
        this.length = length;
        this.startValue = startValue;
        this.endValue = endValue;
        this.easingType = easingType;
        this.easingArgs = easingArgs;
    }

    public Double getLength() {
        return this.length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public T getStartValue() {
        return (T)this.startValue;
    }

    public void setStartValue(T startValue) {
        this.startValue = startValue;
    }

    public T getEndValue() {
        return (T)this.endValue;
    }

    public void setEndValue(T endValue) {
        this.endValue = endValue;
    }

    public int hashCode() {
        return Objects.hash(this.length, this.startValue, this.endValue);
    }

    public boolean equals(Object obj) {
        return obj instanceof KeyFrame && this.hashCode() == obj.hashCode();
    }
}

