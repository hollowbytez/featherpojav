/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.RawAnimation
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.builder;

import java.util.Objects;

public class RawAnimation {
    public String animationName;
    public Boolean loop;

    public RawAnimation(String animationName, Boolean loop) {
        this.animationName = animationName;
        this.loop = loop;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RawAnimation)) {
            return false;
        }
        RawAnimation animation = (RawAnimation)obj;
        return animation.loop == this.loop && animation.animationName.equals(this.animationName);
    }

    public int hashCode() {
        return Objects.hash(this.animationName, this.loop);
    }
}

