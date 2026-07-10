/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.TextureUtil
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.texture.NativeImage
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.util.Identifier
 *  net.minecraft.resource.ResourceManager
 *  v1_21.morecosmetics.models.textures.CustomImage
 */
package v1_21.morecosmetics.models.textures;

import com.mojang.blaze3d.platform.TextureUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import net.minecraft.resource.ResourceManager;

@Environment(value=EnvType.CLIENT)
public class CustomImage
extends AbstractTexture {
    private NativeImage nativeImage;
    private Identifier location;
    private boolean loaded;
    private int width;
    private int height;
    private float factor;

    public CustomImage(Identifier textureResourceLocation, NativeImage img) {
        this.location = textureResourceLocation;
        this.nativeImage = img;
    }

    public CustomImage(Identifier textureResourceLocation, BufferedImage img) {
        this.location = textureResourceLocation;
        this.nativeImage = new NativeImage(img.getWidth(), img.getHeight(), true);
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                int color = img.getRGB(x, y);
                int a = color >> 24 & 0xFF;
                int r = color >> 16 & 0xFF;
                int g = color >> 8 & 0xFF;
                int b = color & 0xFF;
                color = a << 24 | b << 16 | g << 8 | r;
                this.nativeImage.setColor(x, y, color);
            }
        }
        this.setImage(this.nativeImage);
    }

    public void clearGlId() {
        super.clearGlId();
        this.nativeImage = null;
    }

    public int getGlId() {
        int textureId = super.getGlId();
        if (!this.loaded && this.nativeImage != null) {
            this.loaded = true;
            TextureUtil.prepareImage((int)textureId, (int)this.nativeImage.getWidth(), (int)this.nativeImage.getHeight());
            this.nativeImage.upload(0, 0, 0, true);
        }
        return textureId;
    }

    public void load(ResourceManager manager) throws IOException {
    }

    public void delete() {
        this.clearGlId();
    }

    public void updateFactor(float factor) {
        this.factor = factor;
    }

    public void setImage(NativeImage img) {
        this.nativeImage = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.factor = (float)this.width / (float)this.height;
    }

    public float getFactor() {
        return this.factor;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public Identifier getLocation() {
        return this.location;
    }

    public NativeImage getImage() {
        return this.nativeImage;
    }
}

