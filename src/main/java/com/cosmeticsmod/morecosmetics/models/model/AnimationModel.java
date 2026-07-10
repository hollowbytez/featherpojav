/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.animation.Animation
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationType
 *  com.cosmeticsmod.morecosmetics.models.model.AnimationModel
 */
package com.cosmeticsmod.morecosmetics.models.model;

import com.cosmeticsmod.morecosmetics.models.animation.Animation;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationType;
import java.util.HashMap;

public abstract class AnimationModel {
    private HashMap<AnimationType, Animation> animations;

    public Animation getAnimation(AnimationType type) {
        return this.hasAnimation(type) ? (Animation)this.animations.get(type) : new Animation(type);
    }

    public HashMap<AnimationType, Animation> getAnimations() {
        this.checkMapNotNull();
        return this.animations;
    }

    public boolean hasAnimation(AnimationType type) {
        return this.hasAnimations() && this.animations.containsKey(type);
    }

    public void addAnimation(Animation animation) {
        this.checkMapNotNull();
        this.animations.put(animation.getType(), animation);
    }

    public void removeAnimation(AnimationType type) {
        if (this.hasAnimations()) {
            this.animations.remove(type);
        }
    }

    private void checkMapNotNull() {
        if (this.animations == null) {
            this.animations = new HashMap();
        }
    }

    public boolean hasAnimations() {
        return this.animations != null;
    }
}

