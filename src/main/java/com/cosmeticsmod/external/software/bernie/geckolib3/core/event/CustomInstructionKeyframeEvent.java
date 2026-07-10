/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.event;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.KeyframeEvent;

public class CustomInstructionKeyframeEvent<T>
extends KeyframeEvent<T> {
    public final String instructions;

    public CustomInstructionKeyframeEvent(T entity, double animationTick, String instructions, AnimationController controller) {
        super(entity, animationTick, controller);
        this.instructions = instructions;
    }
}

