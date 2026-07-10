/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.NoSerialization
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.morecosmetics.models.config;

import com.cosmeticsmod.morecosmetics.models.config.NoSerialization;
import com.cosmeticsmod.morecosmetics.models.config.SettingOverlay;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

public class ModelData
extends SettingOverlay {
    public SettingOverlay[] model;
    public SettingOverlay[] texture;
    public SettingOverlay[] item;
    @NoSerialization
    @SerializedName(value="v")
    public int version;
    @NoSerialization
    private boolean active = true;

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setSubModels(SettingOverlay[] subModels) {
        this.model = subModels;
    }

    public void setTextureModels(SettingOverlay[] textureModels) {
        this.texture = textureModels;
    }

    public void setItemModels(SettingOverlay[] itemModels) {
        this.item = itemModels;
    }

    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(this.item);
        result = 31 * result + Arrays.hashCode(this.model);
        result = 31 * result + Arrays.hashCode(this.texture);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ModelData other = (ModelData)obj;
        if (!Arrays.equals(this.model, other.model)) {
            return false;
        }
        if (!Arrays.equals(this.texture, other.texture)) {
            return false;
        }
        return Arrays.equals(this.item, other.item);
    }

    public ModelData clone() {
        ModelData clone = new ModelData();
        clone.model = this.model != null ? Arrays.copyOf(this.model, this.model.length) : null;
        clone.texture = this.texture != null ? Arrays.copyOf(this.texture, this.texture.length) : null;
        clone.item = this.item != null ? Arrays.copyOf(this.item, this.item.length) : null;
        clone.version = this.version;
        clone.active = this.active;
        return clone;
    }
}

