/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.TextureModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 */
package com.cosmeticsmod.morecosmetics.models.model;

import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;
import java.awt.image.BufferedImage;

public class TextureModel
extends PositionModel {
    private String url = "";
    private String maskUrl = "";
    private int color = -1;
    private float alpha = 1.0f;
    private BufferedImage mask;
    private int id;
    private int bound = -1;

    public TextureModel(String url, int id) {
        super(ModelPosition.FREE);
        this.url = url;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMaskUrl(String maskUrl) {
        this.maskUrl = maskUrl;
    }

    public String getMaskUrl() {
        return this.maskUrl;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setMask(BufferedImage mask) {
        this.mask = mask;
    }

    public BufferedImage getMask() {
        return this.mask;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public int getBound() {
        return this.bound;
    }

    public boolean isBounded() {
        return this.bound != -1;
    }

    public int getId() {
        return this.id;
    }
}

