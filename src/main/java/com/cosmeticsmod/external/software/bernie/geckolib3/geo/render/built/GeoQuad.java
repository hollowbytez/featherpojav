/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoQuad
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoVertex
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3I
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoVertex;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3I;

public class GeoQuad {
    public GeoVertex[] vertices;
    public final Vec3I normal;
    public final Vec3F normalTransform = new Vec3F(0.0f, 0.0f, 0.0f);
    public EnumFacing direction;

    public GeoQuad(GeoVertex[] verticesIn, float u1, float v1, float uSize, float vSize, float texWidth, float texHeight, Boolean mirrorIn, EnumFacing directionIn) {
        this.direction = directionIn;
        this.vertices = verticesIn;
        float u2 = u1 + uSize;
        float v2 = v1 + vSize;
        u1 /= texWidth;
        u2 /= texWidth;
        v1 /= texHeight;
        v2 /= texHeight;
        if (mirrorIn != null && mirrorIn.booleanValue()) {
            this.vertices[0] = verticesIn[0].setTextureUV(u1, v1);
            this.vertices[1] = verticesIn[1].setTextureUV(u2, v1);
            this.vertices[2] = verticesIn[2].setTextureUV(u2, v2);
            this.vertices[3] = verticesIn[3].setTextureUV(u1, v2);
        } else {
            this.vertices[0] = verticesIn[0].setTextureUV(u2, v1);
            this.vertices[1] = verticesIn[1].setTextureUV(u1, v1);
            this.vertices[2] = verticesIn[2].setTextureUV(u1, v2);
            this.vertices[3] = verticesIn[3].setTextureUV(u2, v2);
        }
        this.normal = directionIn.getDirectionVec();
    }

    public GeoQuad(GeoVertex[] verticesIn, double[] uvCoords, double[] uvSize, float texWidth, float texHeight, Boolean mirrorIn, EnumFacing directionIn) {
        this(verticesIn, (float)uvCoords[0], (float)uvCoords[1], (float)uvSize[0], (float)uvSize[1], texWidth, texHeight, mirrorIn, directionIn);
    }
}

