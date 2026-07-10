/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot
 *  org.apache.commons.lang3.tuple.Pair
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.manager;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;

public class AnimationData {
    private HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection;
    private HashMap<String, AnimationController> animationControllers = new HashMap();
    public double tick;
    public boolean isFirstTick = true;
    private double resetTickLength = 1.0;
    public Double startTick;
    public Object ticker;
    public boolean shouldPlayWhilePaused = false;

    public AnimationData() {
        this.boneSnapshotCollection = new HashMap();
    }

    public AnimationController addAnimationController(AnimationController value) {
        return this.animationControllers.put(value.getName(), value);
    }

    public HashMap<String, Pair<IBone, BoneSnapshot>> getBoneSnapshotCollection() {
        return this.boneSnapshotCollection;
    }

    public void setBoneSnapshotCollection(HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection) {
        this.boneSnapshotCollection = boneSnapshotCollection;
    }

    public void clearSnapshotCache() {
        this.boneSnapshotCollection = new HashMap();
    }

    public double getResetSpeed() {
        return this.resetTickLength;
    }

    public void setResetSpeedInTicks(double resetTickLength) {
        this.resetTickLength = resetTickLength < 0.0 ? 0.0 : resetTickLength;
    }

    public HashMap<String, AnimationController> getAnimationControllers() {
        return this.animationControllers;
    }
}

