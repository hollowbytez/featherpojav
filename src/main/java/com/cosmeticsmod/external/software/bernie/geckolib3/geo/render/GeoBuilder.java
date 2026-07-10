/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Cube
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.GeoBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.IGeoBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoCube
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3D
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.VectorUtils
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Cube;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.IGeoBuilder;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoCube;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3D;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F;
import com.cosmeticsmod.external.software.bernie.geckolib3.util.VectorUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

public class GeoBuilder
implements IGeoBuilder {
    private static Map<String, IGeoBuilder> moddedGeoBuilders = new HashMap();
    private static IGeoBuilder defaultBuilder = new GeoBuilder();

    public static void registerGeoBuilder(String modID, IGeoBuilder builder) {
        moddedGeoBuilders.put(modID, builder);
    }

    public static IGeoBuilder getGeoBuilder(String modID) {
        IGeoBuilder builder = (IGeoBuilder)moddedGeoBuilders.get(modID);
        return builder == null ? defaultBuilder : builder;
    }

    public GeoModel constructGeoModel(RawGeometryTree geometryTree) {
        GeoModel model = new GeoModel();
        model.properties = geometryTree.properties;
        for (RawBoneGroup rawBone : geometryTree.topLevelBones.values()) {
            model.topLevelBones.add(this.constructBone(rawBone, geometryTree.properties, null));
        }
        return model;
    }

    public GeoBone constructBone(RawBoneGroup bone, ModelProperties properties, GeoBone parent) {
        GeoBone geoBone = new GeoBone();
        Bone rawBone = bone.selfBone;
        Vec3F rotation = VectorUtils.convertDoubleToFloat((Vec3D)VectorUtils.fromArray((double[])rawBone.getRotation()));
        Vec3F pivot = VectorUtils.convertDoubleToFloat((Vec3D)VectorUtils.fromArray((double[])rawBone.getPivot()));
        rotation.x *= -1.0f;
        rotation.y *= -1.0f;
        geoBone.mirror = rawBone.getMirror();
        geoBone.dontRender = rawBone.getNeverRender();
        geoBone.reset = rawBone.getReset();
        geoBone.inflate = rawBone.getInflate();
        geoBone.parent = parent;
        geoBone.setModelRendererName(rawBone.getName());
        geoBone.setRotationX((float)Math.toRadians(rotation.getX()));
        geoBone.setRotationY((float)Math.toRadians(rotation.getY()));
        geoBone.setRotationZ((float)Math.toRadians(rotation.getZ()));
        geoBone.rotationPointX = -pivot.getX();
        geoBone.rotationPointY = pivot.getY();
        geoBone.rotationPointZ = pivot.getZ();
        if (!ArrayUtils.isEmpty((Object[])rawBone.getCubes())) {
            for (Cube cube : rawBone.getCubes()) {
                geoBone.childCubes.add(GeoCube.createFromPojoCube((Cube)cube, (ModelProperties)properties, geoBone.inflate == null ? null : Double.valueOf(geoBone.inflate / 16.0), (Boolean)geoBone.mirror));
            }
        }
        for (RawBoneGroup child : bone.children.values()) {
            geoBone.childBones.add(this.constructBone(child, properties, geoBone));
        }
        return geoBone;
    }
}

