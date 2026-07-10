/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoVertex
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.Validate
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class GeoVertex {
    public final Vec3F position;
    public final Vec3F positionTransform = new Vec3F(0.0f, 0.0f, 0.0f);
    public float textureU;
    public float textureV;

    public GeoVertex(float x, float y, float z) {
        this.position = new Vec3F(x, y, z);
    }

    public GeoVertex(double x, double y, double z) {
        this.position = new Vec3F((float)x, (float)y, (float)z);
    }

    public GeoVertex setTextureUV(float texU, float texV) {
        return new GeoVertex(this.position, texU, texV);
    }

    public GeoVertex setTextureUV(double[] array) {
        Validate.validIndex((Object[])ArrayUtils.toObject((double[])array), (int)1);
        return new GeoVertex(this.position, (float)array[0], (float)array[1]);
    }

    public GeoVertex(Vec3F posIn, float texU, float texV) {
        this.position = posIn;
        this.textureU = texU;
        this.textureV = texV;
    }
}

