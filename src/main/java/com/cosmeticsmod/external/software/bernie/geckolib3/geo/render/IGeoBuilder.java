/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.IGeoBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;

public interface IGeoBuilder {
    public GeoModel constructGeoModel(RawGeometryTree var1);

    public GeoBone constructBone(RawBoneGroup var1, ModelProperties var2, GeoBone var3);
}

