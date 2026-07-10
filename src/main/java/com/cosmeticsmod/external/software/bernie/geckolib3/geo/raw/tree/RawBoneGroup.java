/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone;
import java.util.HashMap;

public class RawBoneGroup {
    public HashMap<String, RawBoneGroup> children = new HashMap();
    public Bone selfBone;

    public RawBoneGroup(Bone bone) {
        this.selfBone = bone;
    }
}

