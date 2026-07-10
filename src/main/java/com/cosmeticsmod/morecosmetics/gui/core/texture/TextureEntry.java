/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureEntry
 */
package com.cosmeticsmod.morecosmetics.gui.core.texture;

public class TextureEntry {
    private String name;
    private String imageURL;
    private String previewURL;
    private String creator;
    private int[] colors;

    public TextureEntry(String name, String imageURL) {
        this(name, imageURL, imageURL, null);
    }

    public TextureEntry(String name, String imageURL, String creator) {
        this(name, imageURL, imageURL, creator);
    }

    public TextureEntry(String name, String imageURL, String previewUrl, String creator) {
        this.name = name;
        this.imageURL = imageURL;
        this.previewURL = previewUrl;
        this.creator = creator;
    }

    public boolean hasPreviewImage() {
        return !this.imageURL.equals(this.previewURL);
    }

    public String getName() {
        return this.name;
    }

    public String getPreviewURL() {
        return this.previewURL;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public int[] getColors() {
        return this.colors;
    }

    public boolean hasColors() {
        return this.colors != null;
    }
}

