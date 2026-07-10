/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.EnumNametag
 *  com.cosmeticsmod.morecosmetics.nametags.Nametag
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.morecosmetics.nametags;

import com.cosmeticsmod.morecosmetics.nametags.EnumNametag;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.google.gson.annotations.SerializedName;

public class Nametag {
    @SerializedName(value="tag")
    private String tag;
    @SerializedName(value="stag")
    private String secondTag;
    @SerializedName(value="f")
    private int font = 0;
    @SerializedName(value="m")
    private EnumNametag mode = EnumNametag.SINGLE;
    @SerializedName(value="s")
    private double scale;
    @SerializedName(value="logo")
    private String logoURL;

    public Nametag(String tag) {
        this(tag, 1.0);
    }

    public Nametag(String tag, int num) {
        this(tag, 0.25 + (double)num * 0.25);
    }

    public Nametag(String[] data) {
        this(data[0], Integer.valueOf(data[1]).intValue());
        this.secondTag = Utils.replaceColorCodes((String)data[2]);
        this.mode = EnumNametag.values()[Integer.parseInt(data[3])];
        this.font = Integer.parseInt(data[4]);
        if (data.length > 5) {
            this.logoURL = data[5];
        }
    }

    private Nametag(String tag, double scale) {
        this.tag = Utils.replaceColorCodes((String)tag);
        this.scale = scale;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = Utils.replaceColorCodes((String)tag);
    }

    public String getSecondTag() {
        return this.secondTag;
    }

    public void setSecondTag(String secondTag) {
        this.secondTag = Utils.replaceColorCodes((String)secondTag);
    }

    public boolean hasSecondTag() {
        return this.secondTag != null && !this.secondTag.isEmpty();
    }

    public double getScale() {
        return this.scale;
    }

    public void setScaleNum(int num) {
        this.scale = 0.25 + (double)num * 0.25;
    }

    public int getScaleNum() {
        return (int)((this.scale - 0.25) / 0.25);
    }

    public EnumNametag getMode() {
        return this.mode == null ? EnumNametag.SINGLE : this.mode;
    }

    public void setMode(EnumNametag mode) {
        this.mode = mode;
    }

    public int getFont() {
        return this.font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public boolean hasFont() {
        return this.font > 0;
    }

    public String getLogoURL() {
        return this.logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public boolean hasLogo() {
        return this.logoURL != null && !this.logoURL.isEmpty();
    }
}

