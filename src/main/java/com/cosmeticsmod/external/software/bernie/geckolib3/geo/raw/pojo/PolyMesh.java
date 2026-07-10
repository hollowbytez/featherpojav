/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolyMesh
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysUnion
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysUnion;
import com.google.gson.annotations.SerializedName;

public class PolyMesh {
    @SerializedName(value="normalized_uvs")
    private Boolean normalizedUvs;
    private double[] normals;
    private PolysUnion polys;
    private double[] positions;
    private double[] uvs;

    public Boolean getNormalizedUvs() {
        return this.normalizedUvs;
    }

    public void setNormalizedUvs(Boolean value) {
        this.normalizedUvs = value;
    }

    public double[] getNormals() {
        return this.normals;
    }

    public void setNormals(double[] value) {
        this.normals = value;
    }

    public PolysUnion getPolys() {
        return this.polys;
    }

    public void setPolys(PolysUnion value) {
        this.polys = value;
    }

    public double[] getPositions() {
        return this.positions;
    }

    public void setPositions(double[] value) {
        this.positions = value;
    }

    public double[] getUvs() {
        return this.uvs;
    }

    public void setUvs(double[] value) {
        this.uvs = value;
    }
}

