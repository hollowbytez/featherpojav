/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorClass
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.google.gson.annotations.SerializedName;

public class LocatorClass {
    @SerializedName(value="ignore_inherited_scale")
    private Boolean ignoreInheritedScale;
    private double[] offset;
    private double[] rotation;

    public Boolean getIgnoreInheritedScale() {
        return this.ignoreInheritedScale;
    }

    public void setIgnoreInheritedScale(Boolean value) {
        this.ignoreInheritedScale = value;
    }

    public double[] getOffset() {
        return this.offset;
    }

    public void setOffset(double[] value) {
        this.offset = value;
    }

    public double[] getRotation() {
        return this.rotation;
    }

    public void setRotation(double[] value) {
        this.rotation = value;
    }
}

