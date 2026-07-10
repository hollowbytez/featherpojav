/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.MinecraftGeometry
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import com.google.gson.annotations.SerializedName;

public class MinecraftGeometry {
    private Bone[] bones;
    private String cape;
    @SerializedName(value="description")
    private ModelProperties modelProperties;

    public Bone[] getBones() {
        return this.bones;
    }

    public void setBones(Bone[] value) {
        this.bones = value;
    }

    public String getCape() {
        return this.cape;
    }

    public void setCape(String value) {
        this.cape = value;
    }

    public ModelProperties getProperties() {
        return this.modelProperties;
    }

    public void setProperties(ModelProperties value) {
        this.modelProperties = value;
    }
}

