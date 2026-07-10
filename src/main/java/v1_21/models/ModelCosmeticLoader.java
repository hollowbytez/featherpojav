/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.client.texture.TextureManager
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  v1_21.morecosmetics.models.ModelCosmeticLoader
 *  v1_21.morecosmetics.models.textures.CustomImage
 */
package v1_21.morecosmetics.models;

import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.image.BufferedImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import v1_21.morecosmetics.models.textures.CustomImage;

@Environment(value=EnvType.CLIENT)
public class ModelCosmeticLoader
extends ModelLoader {
    protected void loadTexture(String name, BufferedImage img) {
        Identifier loc = Identifier.of((String)("morecosmetics/" + name));
        TextureManager manager = MinecraftClient.getInstance().getTextureManager();
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> manager.registerTexture(loc, (AbstractTexture)new CustomImage(loc, img)));
        } else {
            manager.registerTexture(loc, (AbstractTexture)new CustomImage(loc, img));
        }
    }
}

