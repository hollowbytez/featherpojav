/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.builder;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    public String animationName;
    public Double animationLength;
    public boolean loop = true;
    public List<BoneAnimation> boneAnimations;
    public List<EventKeyFrame<String>> soundKeyFrames = new ArrayList();
    public List<ParticleEventKeyFrame> particleKeyFrames = new ArrayList();
    public List<EventKeyFrame<String>> customInstructionKeyframes = new ArrayList();
}

