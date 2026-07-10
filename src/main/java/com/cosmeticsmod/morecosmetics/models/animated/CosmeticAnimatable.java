/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.PlayState
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.AnimationBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationFactory
 *  com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel
 *  com.cosmeticsmod.morecosmetics.models.animated.AnimationEventType
 *  com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable
 */
package com.cosmeticsmod.morecosmetics.models.animated;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.PlayState;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.AnimationBuilder;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationFactory;
import com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel;
import com.cosmeticsmod.morecosmetics.models.animated.AnimationEventType;
import java.util.EnumMap;

public class CosmeticAnimatable
implements IAnimatable {
    private AnimationController controller;
    private AnimatedGeoModel model;
    private EnumMap<AnimationEventType, String> animationsMappings = new EnumMap(AnimationEventType.class);
    private String idleAnimation;
    private String lastAnimation;
    public AnimationFactory factory = new AnimationFactory((IAnimatable)this);
    private String controllerName = "mainController";

    public CosmeticAnimatable(AnimatedGeoModel model) {
        this.model = model;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData data) {
        this.controller = new AnimationController((IAnimatable)this, this.controllerName, 5.0f, (AnimationController.IAnimationPredicate)(arg_0 -> this.predicate((com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent)arg_0)));
        String animation = this.model.getAnimationFile().getFirstAnimation();
        if (animation != null) {
            this.idleAnimation = animation;
            this.lastAnimation = animation;
            this.controller.setAnimation(new AnimationBuilder().addAnimation(animation, Boolean.valueOf(true)));
            for (AnimationEventType type : AnimationEventType.VALUES) {
                this.animationsMappings.put(type, this.hasAnimation(type.getName()) ? type.getName() : this.idleAnimation);
            }
        }
        data.addAnimationController(this.controller);
    }

    public void setAnimationEvent(AnimationEventType type) {
        String animation = (String)this.animationsMappings.get(type);
        if (!this.lastAnimation.equals(animation)) {
            String string = animation = animation == null ? this.idleAnimation : animation;
            if (this.hasAnimation(animation)) {
                this.lastAnimation = animation;
                this.controller.setAnimation(new AnimationBuilder().addAnimation(animation, Boolean.valueOf(true)));
            }
        }
    }

    public boolean hasAnimation(String animation) {
        return this.model.getAnimationFile().getAnimation(animation) != null;
    }

    public boolean hasAnimations() {
        return this.idleAnimation != null;
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    public AnimatedGeoModel getModel() {
        return this.model;
    }
}

