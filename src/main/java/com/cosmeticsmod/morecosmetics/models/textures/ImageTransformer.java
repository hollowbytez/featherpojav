/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.textures.ImageTransformer
 */
package com.cosmeticsmod.morecosmetics.models.textures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface ImageTransformer {
    public static final ImageTransformer MOJANG_CLOAK_TRANSFORMER = img -> {
        int imageHeight;
        if (img.getWidth() / 2 == img.getHeight()) {
            return img;
        }
        int imageWidth = 64;
        int srcWidth = img.getWidth();
        int srcHeight = img.getHeight();
        for (imageHeight = 32; imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {
        }
        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return imgNew;
    };
    public static final ImageTransformer NO_TRANSFORM = img -> img;

    public BufferedImage transform(BufferedImage var1);
}

