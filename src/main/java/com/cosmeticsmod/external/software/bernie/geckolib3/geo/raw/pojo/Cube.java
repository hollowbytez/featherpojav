/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Cube
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvUnion
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvUnion;

public class Cube {
    private Double inflate;
    private Boolean mirror;
    private double[] origin = new double[]{0.0, 0.0, 0.0};
    private double[] pivot = new double[]{0.0, 0.0, 0.0};
    private double[] rotation = new double[]{0.0, 0.0, 0.0};
    private double[] size = new double[]{1.0, 1.0, 1.0};
    private UvUnion uv;

    public Double getInflate() {
        return this.inflate;
    }

    public void setInflate(Double value) {
        this.inflate = value;
    }

    public Boolean getMirror() {
        return this.mirror;
    }

    public void setMirror(Boolean value) {
        this.mirror = value;
    }

    public double[] getOrigin() {
        return this.origin;
    }

    public void setOrigin(double[] value) {
        this.origin = value;
    }

    public double[] getPivot() {
        return this.pivot;
    }

    public void setPivot(double[] value) {
        this.pivot = value;
    }

    public double[] getRotation() {
        return this.rotation;
    }

    public void setRotation(double[] value) {
        this.rotation = value;
    }

    public double[] getSize() {
        return this.size;
    }

    public void setSize(double[] value) {
        this.size = value;
    }

    public UvUnion getUv() {
        return this.uv;
    }

    public void setUv(UvUnion value) {
        this.uv = value;
    }
}

