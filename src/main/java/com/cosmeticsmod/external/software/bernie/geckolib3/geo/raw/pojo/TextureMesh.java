/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.TextureMesh
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.google.gson.annotations.SerializedName;

public class TextureMesh {
    @SerializedName(value="local_pivot")
    private double[] localPivot;
    private double[] position;
    private double[] rotation;
    private double[] scale;
    private String texture;

    public double[] getLocalPivot() {
        return this.localPivot;
    }

    public void setLocalPivot(double[] value) {
        this.localPivot = value;
    }

    public double[] getPosition() {
        return this.position;
    }

    public void setPosition(double[] value) {
        this.position = value;
    }

    public double[] getRotation() {
        return this.rotation;
    }

    public void setRotation(double[] value) {
        this.rotation = value;
    }

    public double[] getScale() {
        return this.scale;
    }

    public void setScale(double[] value) {
        this.scale = value;
    }

    public String getTexture() {
        return this.texture;
    }

    public void setTexture(String value) {
        this.texture = value;
    }
}

