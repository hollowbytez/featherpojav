/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel
 *  com.cosmeticsmod.morecosmetics.models.animated.AnimatedCosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable
 */
package com.cosmeticsmod.morecosmetics.models.animated;

import com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel;
import com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable;

public class AnimatedCosmeticModel
extends AnimatedGeoModel<CosmeticAnimatable> {
    public AnimatedCosmeticModel(GeoModel model, AnimationFile animationFile) {
        super(model, animationFile);
    }
}

