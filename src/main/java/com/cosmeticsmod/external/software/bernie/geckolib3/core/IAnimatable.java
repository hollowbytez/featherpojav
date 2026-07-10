/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationFactory
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationData;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.manager.AnimationFactory;

public interface IAnimatable {
    public void registerControllers(AnimationData var1);

    public AnimationFactory getFactory();
}

