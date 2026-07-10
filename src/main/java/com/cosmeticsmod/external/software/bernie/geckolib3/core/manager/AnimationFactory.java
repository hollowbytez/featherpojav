/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationFactory
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.manager;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData;
import java.util.HashMap;

public class AnimationFactory {
    private final IAnimatable animatable;
    private HashMap<Integer, AnimationData> animationDataMap = new HashMap();

    public AnimationFactory(IAnimatable animatable) {
        this.animatable = animatable;
    }

    public AnimationData getOrCreateAnimationData(Integer uniqueID) {
        if (!this.animationDataMap.containsKey(uniqueID)) {
            AnimationData data = new AnimationData();
            this.animatable.registerControllers(data);
            this.animationDataMap.put(uniqueID, data);
        }
        return (AnimationData)this.animationDataMap.get(uniqueID);
    }
}

