/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList;

public class BoneAnimation {
    public String boneName;
    public VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames;
    public VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames;
    public VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames;
}

