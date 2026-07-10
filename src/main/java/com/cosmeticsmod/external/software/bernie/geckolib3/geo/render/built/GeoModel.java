/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeoModel {
    public List<GeoBone> topLevelBones = new ArrayList();
    public ModelProperties properties;

    public Optional<GeoBone> getBone(String name) {
        for (GeoBone bone : this.topLevelBones) {
            GeoBone optionalBone = this.getBoneRecursively(name, bone);
            if (optionalBone == null) continue;
            return Optional.of(optionalBone);
        }
        return Optional.empty();
    }

    private GeoBone getBoneRecursively(String name, GeoBone bone) {
        if (bone.name.equals(name)) {
            return bone;
        }
        for (GeoBone childBone : bone.childBones) {
            if (childBone.name.equals(name)) {
                return childBone;
            }
            GeoBone optionalBone = this.getBoneRecursively(name, childBone);
            if (optionalBone == null) continue;
            return optionalBone;
        }
        return null;
    }
}

