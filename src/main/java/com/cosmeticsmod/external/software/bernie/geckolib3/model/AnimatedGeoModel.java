/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.animation.AnimationTicker
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatableModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.AnimationProcessor
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.utils.GeckoBridge
 *  com.cosmeticsmod.morecosmetics.utils.ITickListener
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.model;

import com.cosmeticsmod.external.software.bernie.geckolib3.animation.AnimationTicker;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatableModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.AnimationProcessor;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;
import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.utils.GeckoBridge;
import com.cosmeticsmod.morecosmetics.utils.ITickListener;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import java.util.Collections;

public abstract class AnimatedGeoModel<T extends IAnimatable>
implements IAnimatableModel<T> {
    public double seekTime;
    public double lastGameTickTime;
    public boolean shouldCrashOnMissing = false;
    private final AnimationProcessor animationProcessor = new AnimationProcessor((IAnimatableModel)this);
    private GeoModel currentModel;
    private AnimationFile animationFile;

    protected AnimatedGeoModel(GeoModel model, AnimationFile animationFile) {
        this.animationProcessor.clearModelRendererList();
        for (GeoBone bone : model.topLevelBones) {
            this.registerBone(bone);
        }
        this.currentModel = model;
        this.animationFile = animationFile;
    }

    public void registerBone(GeoBone bone) {
        this.registerModelRenderer((IBone)bone);
        for (GeoBone childBone : bone.childBones) {
            this.registerBone(childBone);
        }
    }

    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        AnimationData data = entity.getFactory().getOrCreateAnimationData(uniqueID);
        if (data.ticker == null) {
            AnimationTicker ticker = new AnimationTicker(data);
            data.ticker = ticker;
            MoreCosmetics.getInstance().registerTickListener((ITickListener)ticker);
        }
        this.seekTime = data.tick + (double)SharedVars.PARTIAL_TICKS;
        AnimationEvent predicate = customPredicate == null ? new AnimationEvent(entity, 0.0f, 0.0f, 0.0f, false, Collections.emptyList()) : customPredicate;
        predicate.animationTick = this.seekTime;
        this.animationProcessor.preAnimationSetup(predicate.getAnimatable(), this.seekTime);
        if (!this.animationProcessor.getModelRendererList().isEmpty()) {
            this.animationProcessor.tickAnimation(entity, uniqueID, this.seekTime, predicate, GeckoBridge.MOLANG_PARSER, this.shouldCrashOnMissing);
        }
    }

    public AnimationProcessor getAnimationProcessor() {
        return this.animationProcessor;
    }

    public void registerModelRenderer(IBone modelRenderer) {
        this.animationProcessor.registerModelRenderer(modelRenderer);
    }

    public Animation getAnimation(String name, IAnimatable animatable) {
        return this.animationFile.getAnimation(name);
    }

    public GeoModel getModel() {
        return this.currentModel;
    }

    public AnimationFile getAnimationFile() {
        return this.animationFile;
    }

    public void setMolangQueries(IAnimatable animatable, double currentTick) {
    }
}

