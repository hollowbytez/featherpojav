/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.AnimationModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 */
package com.cosmeticsmod.morecosmetics.models.model;

import com.cosmeticsmod.morecosmetics.models.model.AnimationModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;

public abstract class PositionModel
extends AnimationModel {
    private ModelPosition position;
    private float scale = 1.0f;
    private float yaw;
    private float pitch;
    private float roll;
    private float x;
    private float y;
    private float z;

    public PositionModel(ModelPosition position) {
        this.position = position;
    }

    public ModelPosition getPosition() {
        return this.position;
    }

    public void setPosition(ModelPosition position) {
        this.position = position;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return this.roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}

