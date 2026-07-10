/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder
 *  com.cosmeticsmod.morecosmetics.models.textures.ImageMask
 *  com.cosmeticsmod.morecosmetics.models.textures.ImageTransformer
 *  com.cosmeticsmod.morecosmetics.utils.GifDecoder
 *  com.cosmeticsmod.morecosmetics.utils.GifDecoder$ImageFrame
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  org.apache.commons.io.FileUtils
 *  v1_21.morecosmetics.models.textures.CustomGifImage
 *  v1_21.morecosmetics.models.textures.CustomImage
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.models.textures;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.texture.TextureCategoryBuilder;
import com.cosmeticsmod.morecosmetics.models.textures.ImageMask;
import com.cosmeticsmod.morecosmetics.models.textures.ImageTransformer;
import com.cosmeticsmod.morecosmetics.utils.GifDecoder;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;
import v1_21.morecosmetics.models.textures.CustomGifImage;
import v1_21.morecosmetics.models.textures.CustomImage;

@Environment(value=EnvType.CLIENT)
public class CustomTextureManager {
    private static CustomTextureManager globalInstance;
    private String resourceName;
    private HashMap<String, CustomImage> imageLocations = new HashMap();
    private HashMap<String, CustomGifImage> gifLocations = new HashMap();
    private HashMap<String, Identifier> identifiers = new HashMap();

    public CustomTextureManager(String resourceName) {
        this.resourceName = resourceName;
    }

    public Identifier getResource(String identifier, String path) {
        Identifier resource = (Identifier)this.identifiers.get(identifier);
        if (resource == null) {
            resource = Identifier.of((String)path);
            this.identifiers.put(identifier, resource);
        }
        return resource;
    }

    public Identifier getTexture(String identifier, String path) {
        CustomImage image = this.getImage(identifier, path, null);
        return image != null ? image.getLocation() : null;
    }

    public CustomImage getImage(String identifier, String path, BufferedImage mask) {
        return this.getImage(identifier, path, mask, ImageTransformer.NO_TRANSFORM);
    }

    public CustomImage getImage(String identifier, String path, BufferedImage mask, ImageTransformer transformer) {
        boolean jpg;
        if (this.gifLocations.containsKey(identifier)) {
            return ((CustomGifImage)this.gifLocations.get(identifier)).getFrame();
        }
        if (this.imageLocations.containsKey(identifier)) {
            return (CustomImage)this.imageLocations.get(identifier);
        }
        this.imageLocations.put(identifier, null);
        String lowerPath = path.toLowerCase();
        boolean url = lowerPath.startsWith("http");
        boolean bl = jpg = lowerPath.contains(".jpeg") || lowerPath.contains(".jpg");
        if (lowerPath.contains(".png") || jpg) {
            if (url) {
                this.loadImageFromUrl(identifier, path, mask, transformer);
            } else {
                this.loadImageFromFile(identifier, path, mask, transformer);
            }
        } else if (lowerPath.contains(".gif")) {
            if (url) {
                this.loadGifFromUrl(identifier, path, mask, transformer);
            } else {
                this.loadGifFromFile(identifier, path, mask, transformer);
            }
        } else if (url) {
            this.loadContentFromUrl(identifier, path, mask, transformer);
        }
        return null;
    }

    public CustomGifImage getGifImage(String identifier) {
        return (CustomGifImage)this.gifLocations.get(identifier);
    }

    private void loadContentFromUrl(String identifier, String url, BufferedImage mask, ImageTransformer transformer) {
        Identifier location = Identifier.of((String)(this.resourceName + "/" + identifier.hashCode()));
        MoreCosmetics.EXECUTOR.execute(() -> {
            try {
                String[] type = new String[1];
                InputStream stream = Utils.getInputStream((String)url, (int)5000, (String[])type, (Object[])null);
                if (type[0].contains("jpeg") || type[0].contains("png")) {
                    this.handleImage(identifier, location, transformer.transform(ImageIO.read(stream)), mask);
                } else if (type[0].contains("gif")) {
                    this.separateGif(identifier, GifDecoder.readGIF((InputStream)stream), mask, transformer);
                }
            }
            catch (Exception e) {
                MoreCosmetics.log((String)("Failed to load " + url + ": " + e.toString()));
            }
        });
    }

    private void loadImageFromUrl(String identifier, String url, BufferedImage mask, ImageTransformer transformer) {
        Identifier location = Identifier.of((String)(this.resourceName + "/" + identifier.hashCode()));
        MoreCosmetics.EXECUTOR.execute(() -> {
            try {
                InputStream stream = Utils.getInputStream((String)url, (int)5000, (Object[])new Object[0]);
                this.handleImage(identifier, location, transformer.transform(ImageIO.read(stream)), TextureCategoryBuilder.isNoMaskNeeded((String)url) ? null : mask);
            }
            catch (Exception e) {
                MoreCosmetics.log((String)("Failed to load " + url + ": " + e.toString()));
            }
        });
    }

    private void loadImageFromFile(String identifier, String path, BufferedImage mask, ImageTransformer transformer) {
        File target = new File(path);
        if (!target.exists()) {
            MoreCosmetics.log((String)("Failed to load file: " + path));
            return;
        }
        Identifier location = Identifier.of((String)(this.resourceName + "/" + identifier.hashCode()));
        MoreCosmetics.EXECUTOR.execute(() -> {
            try {
                this.handleImage(identifier, location, transformer.transform(ImageIO.read(target)), mask);
            }
            catch (IOException e) {
                MoreCosmetics.log((String)("Failed to load " + path + ": " + e.toString()));
            }
        });
    }

    private void loadGifFromUrl(String identifier, String url, BufferedImage mask, ImageTransformer transformer) {
        MoreCosmetics.EXECUTOR.execute(() -> {
            try {
                InputStream stream = Utils.getInputStream((String)url, (int)5000, (Object[])new Object[0]);
                this.separateGif(identifier, GifDecoder.readGIF((InputStream)stream), mask, transformer);
            }
            catch (Exception e) {
                MoreCosmetics.log((String)("Failed to load " + url + ": " + e.toString()));
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        });
    }

    private void loadGifFromFile(String identifier, String path, BufferedImage mask, ImageTransformer transformer) {
        File file = new File(path);
        if (!file.exists()) {
            MoreCosmetics.log((String)("File not found: " + path));
            return;
        }
        MoreCosmetics.EXECUTOR.execute(() -> {
            try {
                this.separateGif(identifier, GifDecoder.readGIF((InputStream)FileUtils.openInputStream((File)file)), mask, transformer);
            }
            catch (IOException e) {
                MoreCosmetics.log((String)("Failed to load " + path + ": " + e.toString()));
            }
        });
    }

    private void handleImage(String identifier, Identifier location, BufferedImage img, BufferedImage mask) {
        if (mask != null) {
            ImageMask imageMask = new ImageMask(mask, img.getWidth(), img.getHeight(), true);
            img = imageMask.applyMask(img);
        }
        CustomImage image = new CustomImage(location, img);
        if (mask != null) {
            image.updateFactor((float)mask.getWidth() / (float)mask.getHeight());
        }
        this.runOnRenderThread(() -> {
            MinecraftClient.getInstance().getTextureManager().registerTexture(location, (AbstractTexture)image);
            this.imageLocations.put(identifier, image);
        });
    }

    private void separateGif(String identifier, GifDecoder.ImageFrame[] imageFrames, BufferedImage mask, ImageTransformer transformer) {
        int frames = imageFrames.length;
        int[] delays = new int[frames];
        CustomImage[] images = new CustomImage[frames];
        ImageMask imageMask = null;
        String prefix = this.resourceName + "/gif/" + identifier.hashCode() + "_";
        for (int i = 0; i < frames; ++i) {
            int delay;
            GifDecoder.ImageFrame gif = imageFrames[i];
            BufferedImage img = transformer.transform(gif.getImage());
            if (i == 0 && mask != null) {
                imageMask = new ImageMask(mask, img.getWidth(), img.getHeight());
            }
            delays[i] = (delay = gif.getDelay()) == 0 ? 50 : delay * 10;
            Identifier location = Identifier.of((String)(prefix + i));
            img = imageMask == null ? img : imageMask.applyMask(img);
            CustomImage image = images[i] = new CustomImage(location, img);
            if (mask != null) {
                image.updateFactor((float)mask.getWidth() / (float)mask.getHeight());
            }
            int count = i + 1;
            this.runOnRenderThread(() -> {
                MinecraftClient.getInstance().getTextureManager().registerTexture(location, (AbstractTexture)image);
                if (count == frames) {
                    this.gifLocations.put(identifier, new CustomGifImage(images, delays));
                }
            });
        }
    }

    private void runOnRenderThread(Runnable callback) {
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(callback::run);
        } else {
            callback.run();
        }
    }

    public HashMap<String, CustomImage> getImageLocations() {
        return this.imageLocations;
    }

    public HashMap<String, Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public static CustomTextureManager getGlobalInstance() {
        return globalInstance != null ? globalInstance : (globalInstance = new CustomTextureManager("morecosmetics"));
    }

    public void reset() {
        this.imageLocations.clear();
        this.gifLocations.clear();
        this.identifiers.clear();
    }

    public void remove(String identifier) {
        this.imageLocations.remove(identifier);
        this.gifLocations.remove(identifier);
        this.identifiers.remove(identifier);
    }
}

