/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontData
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  com.cosmeticsmod.morecosmetics.nametags.font.Glyph
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.client.render.BufferRenderer
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.Tessellator
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.client.render.VertexFormat$class_5596
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.font.TextRenderer$class_6415
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.VertexConsumerProvider$class_4598
 *  net.minecraft.client.render.OverlayTexture
 *  net.minecraft.client.render.GameRenderer
 *  net.minecraft.client.render.BuiltBuffer
 *  org.joml.Matrix4f
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomImage
 *  v1_21.morecosmetics.nametags.FontImageRenderer
 */
package v1_21.morecosmetics.nametags;

import com.cosmeticsmod.morecosmetics.nametags.font.FontData;
import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import com.cosmeticsmod.morecosmetics.nametags.font.Glyph;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.BuiltBuffer;
import org.joml.Matrix4f;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomImage;

@Environment(value=EnvType.CLIENT)
public class FontImageRenderer
extends FontImage {
    private final CustomImage texture;
    private final Identifier id;
    private boolean lightning;
    private int light;
    private float posX;
    private float posY;
    private float red;
    private float r;
    private float green;
    private float g;
    private float blue;
    private float b;
    private float alpha;

    public FontImageRenderer(FontData fontData, int[] colorCode) {
        super(fontData, colorCode);
        this.id = Identifier.of((String)("cmf/" + fontData.id));
        this.texture = new CustomImage(this.id, fontData.image);
        MinecraftClient.getInstance().getTextureManager().registerTexture(this.id, (AbstractTexture)this.texture);
    }

    public void enableLightning(int light) {
        this.light = light;
        this.lightning = true;
    }

    public void disableLightning() {
        this.lightning = false;
    }

    public int renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (text == null) {
            return 0;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (dropShadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }
        this.red = this.r = (float)(color >> 16 & 0xFF) / 255.0f;
        this.green = this.g = (float)(color >> 8 & 0xFF) / 255.0f;
        this.blue = this.b = (float)(color & 0xFF) / 255.0f;
        this.alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        this.posX = x / this.scale;
        this.posY = y / this.scale - (float)this.fontData.height * this.scale;
        this.renderStringAtPos(text, dropShadow);
        return (int)((this.posX + (float)this.fontData.offset) * this.scale);
    }

    private void renderStringAtPos(String text, boolean shadow) {
        StackHolder stack = StackHolder.getInstance();
        stack.push();
        stack.scale(this.scale, this.scale, this.scale);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((int)770, (int)771);
        RenderSystem.bindTexture((int)this.texture.getGlId());
        RenderSystem.setShaderTexture((int)0, (int)this.texture.getGlId());
        MinecraftClient.getInstance().getTextureManager().bindTexture(this.id);
        String lookup = text.toLowerCase();
        for (int i = 0; i < text.length(); ++i) {
            char c0 = text.charAt(i);
            if (c0 == '\u00a7' && i + 1 < text.length()) {
                int i1 = "0123456789abcdefklmnor".indexOf(lookup.charAt(i + 1));
                if (i1 < 16) {
                    this.resetStyles();
                    if (i1 < 0) {
                        i1 = 15;
                    }
                    if (shadow) {
                        i1 += 16;
                    }
                    int j1 = this.colorCode[i1];
                    this.r = (float)(j1 >> 16) / 255.0f;
                    this.g = (float)(j1 >> 8 & 0xFF) / 255.0f;
                    this.b = (float)(j1 & 0xFF) / 255.0f;
                } else if (i1 == 16) {
                    this.randomStyle = true;
                } else if (i1 == 17) {
                    this.boldStyle = true;
                } else if (i1 == 18) {
                    this.strikethroughStyle = true;
                } else if (i1 == 19) {
                    this.underlineStyle = true;
                } else if (i1 == 20) {
                    this.italicStyle = true;
                } else {
                    this.resetStyles();
                    this.r = this.red;
                    this.g = this.green;
                    this.b = this.blue;
                }
                ++i;
                continue;
            }
            this.posX += this.drawChar(c0, this.posX, this.posY);
        }
        stack.pop();
    }

    private float drawChar(char ch, float x, float y) {
        StackHolder stack = StackHolder.getInstance();
        Glyph glyph = this.getGlyph(ch);
        if (glyph == null) {
            stack.push();
            stack.scale(1.0f / this.scale, 1.0f / this.scale, 1.0f / this.scale);
            VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            MinecraftClient.getInstance().textRenderer.draw(Character.toString(ch), x * this.scale + 0.5f, (y + (float)this.fontData.height * this.scale) * this.scale, Utils.toRGB((float)this.r, (float)this.g, (float)this.b, (float)this.alpha), false, ((MatrixStack)stack.get()).peek().getPositionMatrix(), (VertexConsumerProvider)consumerProvider, TextRenderer.TextLayerType.NORMAL, 0, this.lightning ? SharedVars.LIGHT : 0xF000F0);
            consumerProvider.draw();
            stack.pop();
            RenderSystem.bindTexture((int)this.texture.getGlId());
            RenderSystem.setShaderTexture((int)0, (int)this.texture.getGlId());
            MinecraftClient.getInstance().getTextureManager().bindTexture(this.id);
            return this.getDefaultWidth(ch);
        }
        float iX = (float)glyph.x / (float)this.imageSize;
        float iY = (float)glyph.y / (float)this.imageSize;
        float iWidth = (float)(glyph.width - 4) / (float)this.imageSize;
        float iHeight = (float)(glyph.height - 4) / (float)this.imageSize;
        float width = glyph.width - 4;
        float height = glyph.height - 4;
        float f = glyph.width - this.fontData.offset;
        Matrix4f matrix = ((MatrixStack)stack.get()).peek().getPositionMatrix();
        if (this.lightning) {
            int overlay = OverlayTexture.DEFAULT_UV;
            VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            VertexConsumer bufferBuilder = consumerProvider.getBuffer(RenderLayer.getEntityTranslucent((Identifier)this.id));
            bufferBuilder.vertex(matrix, x + width, y, 0.0f).color(this.r, this.g, this.b, this.alpha).texture(iX + iWidth, iY).overlay(overlay).light(this.light).normal(1.0f, 1.0f, 1.0f);
            bufferBuilder.vertex(matrix, x, y, 0.0f).color(this.r, this.g, this.b, this.alpha).texture(iX, iY).overlay(overlay).light(this.light).normal(1.0f, 1.0f, 1.0f);
            bufferBuilder.vertex(matrix, x, y + height, 0.0f).color(this.r, this.g, this.b, this.alpha).texture(iX, iY + iHeight).overlay(overlay).light(this.light).normal(1.0f, 1.0f, 1.0f);
            bufferBuilder.vertex(matrix, x + width, y + height, 0.0f).color(this.r, this.g, this.b, this.alpha).texture(iX + iWidth, iY + iHeight).overlay(overlay).light(this.light).normal(1.0f, 1.0f, 1.0f);
        } else {
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            bufferBuilder.vertex(matrix, x + width, y, 0.0f).texture(iX + iWidth, iY).color(this.r, this.g, this.b, this.alpha);
            bufferBuilder.vertex(matrix, x, y, 0.0f).texture(iX, iY).color(this.r, this.g, this.b, this.alpha);
            bufferBuilder.vertex(matrix, x, y + height, 0.0f).texture(iX, iY + iHeight).color(this.r, this.g, this.b, this.alpha);
            bufferBuilder.vertex(matrix, x + width, y + height, 0.0f).texture(iX + iWidth, iY + iHeight).color(this.r, this.g, this.b, this.alpha);
            BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferBuilder.end());
        }
        return f;
    }

    public int getDefaultWidth(char ch) {
        return (int)((float)MinecraftClient.getInstance().textRenderer.getWidth(Character.toString(ch)) / this.scale);
    }
}

