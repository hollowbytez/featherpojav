/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFileLoader
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.GeoModelLoader
 *  com.cosmeticsmod.external.software.bernie.geckolib3.molang.MolangRegistrar
 *  com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable
 *  com.cosmeticsmod.morecosmetics.utils.GeckoBridge
 */
package com.cosmeticsmod.morecosmetics.utils;

import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFileLoader;
import com.cosmeticsmod.external.software.bernie.geckolib3.file.GeoModelLoader;
import com.cosmeticsmod.external.software.bernie.geckolib3.molang.MolangRegistrar;
import com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable;

public class GeckoBridge {
    public static final MolangParser MOLANG_PARSER = new MolangParser();
    public static final AnimationFileLoader ANIMATION_LOADER = new AnimationFileLoader();
    public static final GeoModelLoader MODEL_LOADER = new GeoModelLoader();

    public static void init() {
        MolangRegistrar.registerVars((MolangParser)MOLANG_PARSER);
        AnimationController.addModelFetcher(object -> {
            if (object instanceof CosmeticAnimatable) {
                return ((CosmeticAnimatable)object).getModel();
            }
            return null;
        });
    }
}

