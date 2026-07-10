/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatableModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimationQueue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.AnimationProcessor
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.DirtyTracker
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.util.MathUtil
 *  org.apache.commons.lang3.tuple.Pair
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.processor;

import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatableModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimationQueue;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.DirtyTracker;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.util.MathUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

public class AnimationProcessor<T extends IAnimatable> {
    public boolean reloadAnimations = false;
    private List<IBone> modelRendererList = new ArrayList();
    private double lastTickValue = -1.0;
    private Set<Integer> animatedEntities = new HashSet();
    private final IAnimatableModel animatedModel;

    public AnimationProcessor(IAnimatableModel animatedModel) {
        this.animatedModel = animatedModel;
    }

    public void tickAnimation(IAnimatable entity, Integer uniqueID, double seekTime, AnimationEvent event, MolangParser parser, boolean crashWhenCantFindBone) {
        if (seekTime != this.lastTickValue) {
            this.animatedEntities.clear();
        } else if (this.animatedEntities.contains(uniqueID)) {
            return;
        }
        this.lastTickValue = seekTime;
        this.animatedEntities.add(uniqueID);
        AnimationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
        HashMap modelTracker = this.createNewDirtyTracker();
        this.updateBoneSnapshots(manager.getBoneSnapshotCollection());
        HashMap boneSnapshots = manager.getBoneSnapshotCollection();
        for (AnimationController controller : manager.getAnimationControllers().values()) {
            if (this.reloadAnimations) {
                controller.markNeedsReload();
                controller.getBoneAnimationQueues().clear();
            }
            controller.isJustStarting = manager.isFirstTick;
            event.setController(controller);
            controller.process(seekTime, event, this.modelRendererList, boneSnapshots, parser, crashWhenCantFindBone);
            for (Object boneAnimObj : controller.getBoneAnimationQueues().values()) {
                BoneAnimationQueue boneAnimation = (BoneAnimationQueue)boneAnimObj;
                IBone bone = boneAnimation.bone;
                BoneSnapshot snapshot = (BoneSnapshot)((Pair)boneSnapshots.get(bone.getName())).getRight();
                BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
                AnimationPoint rXPoint = (AnimationPoint)boneAnimation.rotationXQueue.poll();
                AnimationPoint rYPoint = (AnimationPoint)boneAnimation.rotationYQueue.poll();
                AnimationPoint rZPoint = (AnimationPoint)boneAnimation.rotationZQueue.poll();
                AnimationPoint pXPoint = (AnimationPoint)boneAnimation.positionXQueue.poll();
                AnimationPoint pYPoint = (AnimationPoint)boneAnimation.positionYQueue.poll();
                AnimationPoint pZPoint = (AnimationPoint)boneAnimation.positionZQueue.poll();
                AnimationPoint sXPoint = (AnimationPoint)boneAnimation.scaleXQueue.poll();
                AnimationPoint sYPoint = (AnimationPoint)boneAnimation.scaleYQueue.poll();
                AnimationPoint sZPoint = (AnimationPoint)boneAnimation.scaleZQueue.poll();
                DirtyTracker dirtyTracker = (DirtyTracker)modelTracker.get(bone.getName());
                if (dirtyTracker == null) continue;
                if (rXPoint != null && rYPoint != null && rZPoint != null) {
                    bone.setRotationX(MathUtil.lerpValues((AnimationPoint)rXPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod) + initialSnapshot.rotationValueX);
                    bone.setRotationY(MathUtil.lerpValues((AnimationPoint)rYPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod) + initialSnapshot.rotationValueY);
                    bone.setRotationZ(MathUtil.lerpValues((AnimationPoint)rZPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod) + initialSnapshot.rotationValueZ);
                    snapshot.rotationValueX = bone.getRotationX();
                    snapshot.rotationValueY = bone.getRotationY();
                    snapshot.rotationValueZ = bone.getRotationZ();
                    snapshot.isCurrentlyRunningRotationAnimation = true;
                    dirtyTracker.hasRotationChanged = true;
                }
                if (pXPoint != null && pYPoint != null && pZPoint != null) {
                    bone.setPositionX(MathUtil.lerpValues((AnimationPoint)pXPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod));
                    bone.setPositionY(MathUtil.lerpValues((AnimationPoint)pYPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod));
                    bone.setPositionZ(MathUtil.lerpValues((AnimationPoint)pZPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod));
                    snapshot.positionOffsetX = bone.getPositionX();
                    snapshot.positionOffsetY = bone.getPositionY();
                    snapshot.positionOffsetZ = bone.getPositionZ();
                    snapshot.isCurrentlyRunningPositionAnimation = true;
                    dirtyTracker.hasPositionChanged = true;
                }
                if (sXPoint == null || sYPoint == null || sZPoint == null) continue;
                bone.setScaleX(MathUtil.lerpValues((AnimationPoint)sXPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod));
                bone.setScaleY(MathUtil.lerpValues((AnimationPoint)sYPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod));
                bone.setScaleZ(MathUtil.lerpValues((AnimationPoint)sZPoint, (EasingType)controller.easingType, (Function)controller.customEasingMethod));
                snapshot.scaleValueX = bone.getScaleX();
                snapshot.scaleValueY = bone.getScaleY();
                snapshot.scaleValueZ = bone.getScaleZ();
                snapshot.isCurrentlyRunningScaleAnimation = true;
                dirtyTracker.hasScaleChanged = true;
            }
        }
        this.reloadAnimations = false;
        double resetTickLength = manager.getResetSpeed();
        for (Object trackerObj : modelTracker.entrySet()) {
            Map.Entry tracker = (Map.Entry)trackerObj;
            double percentageReset;
            IBone model = ((DirtyTracker)tracker.getValue()).model;
            BoneSnapshot initialSnapshot = model.getInitialSnapshot();
            BoneSnapshot saveSnapshot = (BoneSnapshot)((Pair)boneSnapshots.get(tracker.getKey())).getRight();
            if (saveSnapshot == null) {
                if (!crashWhenCantFindBone) continue;
                throw new RuntimeException("Could not find save snapshot for bone: " + ((DirtyTracker)tracker.getValue()).model.getName() + ". Please don't add bones that are used in an animation at runtime.");
            }
            if (!((DirtyTracker)tracker.getValue()).hasRotationChanged) {
                if (saveSnapshot.isCurrentlyRunningRotationAnimation) {
                    saveSnapshot.mostRecentResetRotationTick = (float)seekTime;
                    saveSnapshot.isCurrentlyRunningRotationAnimation = false;
                }
                percentageReset = Math.min((seekTime - (double)saveSnapshot.mostRecentResetRotationTick) / resetTickLength, 1.0);
                model.setRotationX(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.rotationValueX, (double)initialSnapshot.rotationValueX));
                model.setRotationY(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.rotationValueY, (double)initialSnapshot.rotationValueY));
                model.setRotationZ(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.rotationValueZ, (double)initialSnapshot.rotationValueZ));
                if (percentageReset >= 1.0) {
                    saveSnapshot.rotationValueX = model.getRotationX();
                    saveSnapshot.rotationValueY = model.getRotationY();
                    saveSnapshot.rotationValueZ = model.getRotationZ();
                }
            }
            if (!((DirtyTracker)tracker.getValue()).hasPositionChanged) {
                if (saveSnapshot.isCurrentlyRunningPositionAnimation) {
                    saveSnapshot.mostRecentResetPositionTick = (float)seekTime;
                    saveSnapshot.isCurrentlyRunningPositionAnimation = false;
                }
                percentageReset = Math.min((seekTime - (double)saveSnapshot.mostRecentResetPositionTick) / resetTickLength, 1.0);
                model.setPositionX(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.positionOffsetX, (double)initialSnapshot.positionOffsetX));
                model.setPositionY(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.positionOffsetY, (double)initialSnapshot.positionOffsetY));
                model.setPositionZ(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.positionOffsetZ, (double)initialSnapshot.positionOffsetZ));
                if (percentageReset >= 1.0) {
                    saveSnapshot.positionOffsetX = model.getPositionX();
                    saveSnapshot.positionOffsetY = model.getPositionY();
                    saveSnapshot.positionOffsetZ = model.getPositionZ();
                }
            }
            if (((DirtyTracker)tracker.getValue()).hasScaleChanged) continue;
            if (saveSnapshot.isCurrentlyRunningScaleAnimation) {
                saveSnapshot.mostRecentResetScaleTick = (float)seekTime;
                saveSnapshot.isCurrentlyRunningScaleAnimation = false;
            }
            percentageReset = Math.min((seekTime - (double)saveSnapshot.mostRecentResetScaleTick) / resetTickLength, 1.0);
            model.setScaleX(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.scaleValueX, (double)initialSnapshot.scaleValueX));
            model.setScaleY(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.scaleValueY, (double)initialSnapshot.scaleValueY));
            model.setScaleZ(MathUtil.lerpValues((double)percentageReset, (double)saveSnapshot.scaleValueZ, (double)initialSnapshot.scaleValueZ));
            if (!(percentageReset >= 1.0)) continue;
            saveSnapshot.scaleValueX = model.getScaleX();
            saveSnapshot.scaleValueY = model.getScaleY();
            saveSnapshot.scaleValueZ = model.getScaleZ();
        }
        manager.isFirstTick = false;
    }

    private HashMap<String, DirtyTracker> createNewDirtyTracker() {
        HashMap<String, DirtyTracker> tracker = new HashMap<String, DirtyTracker>();
        for (IBone bone : this.modelRendererList) {
            tracker.put(bone.getName(), new DirtyTracker(false, false, false, bone));
        }
        return tracker;
    }

    private void updateBoneSnapshots(HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection) {
        for (IBone bone : this.modelRendererList) {
            if (boneSnapshotCollection.containsKey(bone.getName())) continue;
            boneSnapshotCollection.put(bone.getName(), Pair.of(bone, new BoneSnapshot(bone.getInitialSnapshot())));
        }
    }

    public IBone getBone(String boneName) {
        return this.modelRendererList.stream().filter(x -> x.getName().equals(boneName)).findFirst().orElse(null);
    }

    public void registerModelRenderer(IBone modelRenderer) {
        modelRenderer.saveInitialSnapshot();
        this.modelRendererList.add(modelRenderer);
    }

    public void clearModelRendererList() {
        this.modelRendererList.clear();
    }

    public List<IBone> getModelRendererList() {
        return this.modelRendererList;
    }

    public void preAnimationSetup(IAnimatable animatable, double seekTime) {
        this.animatedModel.setMolangQueries(animatable, seekTime);
    }
}

