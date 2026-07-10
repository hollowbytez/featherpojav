/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.AnimationBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.RawAnimation
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.builder;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.RawAnimation;
import java.util.ArrayList;
import java.util.List;

public class AnimationBuilder {
    private List<RawAnimation> animationList = new ArrayList();

    public AnimationBuilder addAnimation(String animationName, Boolean shouldLoop) {
        this.animationList.add(new RawAnimation(animationName, shouldLoop));
        return this;
    }

    public AnimationBuilder addAnimation(String animationName) {
        this.animationList.add(new RawAnimation(animationName, null));
        return this;
    }

    public AnimationBuilder addRepeatingAnimation(String animationName, int timesToRepeat) {
        assert (timesToRepeat > 0);
        for (int i = 0; i < timesToRepeat; ++i) {
            this.addAnimation(animationName, Boolean.valueOf(false));
        }
        return this;
    }

    public AnimationBuilder clearAnimations() {
        this.animationList.clear();
        return this;
    }

    public List<RawAnimation> getRawAnimationList() {
        return this.animationList;
    }
}

