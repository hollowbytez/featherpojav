/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.config.DefaultValue
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 */
package com.cosmeticsmod.morecosmetics.models.config;

import com.cosmeticsmod.morecosmetics.models.config.DefaultValue;

public class SettingOverlay {
    @DefaultValue
    public float x;
    @DefaultValue
    public float y;
    @DefaultValue
    public float z;
    @DefaultValue
    public float yaw;
    @DefaultValue
    public float pitch;
    @DefaultValue
    public float roll;
    @DefaultValue(value=1.0f)
    public float scale = 1.0f;
    @DefaultValue(value=1.0f)
    public float mulitply = 1.0f;
    @DefaultValue
    public int color;
    @DefaultValue
    public String url;
    @DefaultValue
    public int id;
    @DefaultValue
    public boolean illum;
    @DefaultValue
    public boolean resize;
    @DefaultValue(value=1.0f)
    public boolean visible = true;
    @DefaultValue(value=1.0f)
    public boolean animation = true;

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.animation ? 1231 : 1237);
        result = 31 * result + this.color;
        result = 31 * result + this.id;
        result = 31 * result + (this.illum ? 1231 : 1237);
        result = 31 * result + Float.floatToIntBits(this.mulitply);
        result = 31 * result + Float.floatToIntBits(this.pitch);
        result = 31 * result + (this.resize ? 1231 : 1237);
        result = 31 * result + Float.floatToIntBits(this.roll);
        result = 31 * result + Float.floatToIntBits(this.scale);
        result = 31 * result + (this.url == null ? 0 : this.url.hashCode());
        result = 31 * result + (this.visible ? 1231 : 1237);
        result = 31 * result + Float.floatToIntBits(this.x);
        result = 31 * result + Float.floatToIntBits(this.y);
        result = 31 * result + Float.floatToIntBits(this.yaw);
        result = 31 * result + Float.floatToIntBits(this.z);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SettingOverlay other = (SettingOverlay)obj;
        if (this.animation != other.animation) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.illum != other.illum) {
            return false;
        }
        if (Float.floatToIntBits(this.mulitply) != Float.floatToIntBits(other.mulitply)) {
            return false;
        }
        if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
            return false;
        }
        if (this.resize != other.resize) {
            return false;
        }
        if (Float.floatToIntBits(this.roll) != Float.floatToIntBits(other.roll)) {
            return false;
        }
        if (Float.floatToIntBits(this.scale) != Float.floatToIntBits(other.scale)) {
            return false;
        }
        if (this.url == null ? other.url != null : !this.url.equals(other.url)) {
            return false;
        }
        if (this.visible != other.visible) {
            return false;
        }
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
            return false;
        }
        return Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
    }
}

