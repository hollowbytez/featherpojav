/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.file;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class AnimationFile {
    private HashMap<String, Animation> animations = new HashMap();

    public Animation getAnimation(String name) {
        return (Animation)this.animations.get(name);
    }

    public void putAnimation(String name, Animation animation) {
        this.animations.put(name, animation);
    }

    public Collection<Animation> getAllAnimations() {
        return this.animations.values();
    }

    public String getFirstAnimation() {
        Optional first = this.animations.keySet().stream().findFirst();
        return first.isPresent() ? (String)first.get() : null;
    }
}

