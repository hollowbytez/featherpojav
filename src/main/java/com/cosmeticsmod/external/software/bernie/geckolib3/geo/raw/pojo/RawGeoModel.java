/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.FormatVersion
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.MinecraftGeometry
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.FormatVersion;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.MinecraftGeometry;
import com.google.gson.annotations.SerializedName;

public class RawGeoModel {
    @SerializedName(value="format_version")
    private FormatVersion formatVersion;
    @SerializedName(value="minecraft:geometry")
    private MinecraftGeometry[] minecraftGeometry;

    public FormatVersion getFormatVersion() {
        return this.formatVersion;
    }

    public void setFormatVersion(FormatVersion value) {
        this.formatVersion = value;
    }

    public MinecraftGeometry[] getMinecraftGeometry() {
        return this.minecraftGeometry;
    }

    public void setMinecraftGeometry(MinecraftGeometry[] value) {
        this.minecraftGeometry = value;
    }
}

