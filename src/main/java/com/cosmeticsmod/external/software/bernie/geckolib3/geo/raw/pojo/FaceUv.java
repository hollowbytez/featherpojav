/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.FaceUv
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.google.gson.annotations.SerializedName;

public class FaceUv {
    @SerializedName(value="material_instance")
    private String materialInstance;
    private double[] uv;
    @SerializedName(value="uv_size")
    private double[] uvSize;

    public String getMaterialInstance() {
        return this.materialInstance;
    }

    public void setMaterialInstance(String value) {
        this.materialInstance = value;
    }

    public double[] getUv() {
        return this.uv;
    }

    public void setUv(double[] value) {
        this.uv = value;
    }

    public double[] getUvSize() {
        return this.uvSize;
    }

    public void setUvSize(double[] value) {
        this.uvSize = value;
    }
}

