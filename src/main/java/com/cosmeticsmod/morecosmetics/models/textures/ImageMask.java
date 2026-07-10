/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.textures.ImageMask
 */
package com.cosmeticsmod.morecosmetics.models.textures;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ImageMask {
    private ArrayList<int[]> list;
    private BufferedImage mask;
    private int width;
    private int height;

    public ImageMask(BufferedImage mask, int width, int height) {
        this(mask, width, height, false);
    }

    public ImageMask(BufferedImage mask, int width, int height, boolean single) {
        this.width = width;
        this.height = height;
        if (width != mask.getWidth() || height != mask.getHeight()) {
            mask = this.scaleImage(mask, width, height);
        }
        if (single) {
            this.mask = mask;
        } else {
            this.list = new ArrayList(100);
            WritableRaster raster = mask.getRaster();
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    if (((byte[])raster.getDataElements(x, y, null))[0] != 0) continue;
                    this.list.add(new int[]{x, y});
                }
            }
        }
    }

    public BufferedImage applyMask(BufferedImage image) {
        if (this.list != null) {
            for (int[] mask : this.list) {
                image.setRGB(mask[0], mask[1], 0);
            }
            return image;
        }
        boolean alphaNeeded = !image.getColorModel().hasAlpha();
        BufferedImage result = alphaNeeded ? new BufferedImage(this.width, this.height, 2) : image;
        WritableRaster raster = this.mask.getRaster();
        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                if (((byte[])raster.getDataElements(x, y, null))[0] == 0) {
                    result.setRGB(x, y, 0);
                    continue;
                }
                if (!alphaNeeded) continue;
                result.setRGB(x, y, image.getRGB(x, y));
            }
        }
        return result;
    }

    private BufferedImage scaleImage(BufferedImage img, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height, 12);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        return scaled;
    }
}

