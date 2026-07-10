/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.core.UIConstants
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.cosmeticsmod.morecosmetics.utils.Utils
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.client.render.BufferRenderer
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.Tessellator
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.client.render.VertexFormat$class_5596
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.render.DiffuseLighting
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.font.TextRenderer
 *  net.minecraft.client.font.TextRenderer$class_6415
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.VertexConsumerProvider$class_4598
 *  net.minecraft.client.render.OverlayTexture
 *  net.minecraft.util.math.ColorHelper$class_5254
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  net.minecraft.client.render.GameRenderer
 *  net.minecraft.client.render.entity.EntityRenderDispatcher
 *  net.minecraft.client.render.BuiltBuffer
 *  org.joml.Matrix4f
 *  org.joml.Quaternionf
 *  org.lwjgl.opengl.GL11
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 *  v1_21.morecosmetics.models.renderer.QuaternionHelper
 *  v1_21.morecosmetics.models.renderer.StackHolder
 */
package v1_21.morecosmetics;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.core.UIConstants;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.cosmeticsmod.morecosmetics.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.BuiltBuffer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;
import v1_21.morecosmetics.models.renderer.QuaternionHelper;
import v1_21.morecosmetics.models.renderer.StackHolder;

/*
 * Exception performing whole class analysis ignored.
 */
@Environment(value=EnvType.CLIENT)
public class DrawUtils {
    private static CustomFontRenderer customFontRenderer;

    public static void drawTexture(float x, float y, float imageWidth, float imageHeight, float maxWidth, float maxHeight, float alpha, int color, Identifier id) {
        MatrixStack stack = StackHolder.STACK;
        stack.push();
        float sizeWidth = maxWidth / imageWidth;
        float sizeHeight = maxHeight / imageHeight;
        stack.scale(sizeWidth, sizeHeight, 0.0f);
        if (alpha < 1.0f || color != -1) {
            RenderSystem.enableBlend();
            if (color != -1) {
                RenderSystem.setShaderColor((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
            } else {
                RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
            }
        }
        RenderSystem.setShaderTexture((int)0, (Identifier)id);
        DrawUtils.drawTexturedModalRect((Matrix4f)stack.peek().getPositionMatrix(), (float)(x / sizeWidth), (float)(y / sizeHeight), (float)(x / sizeWidth + imageWidth), (float)(y / sizeHeight + imageHeight));
        if (alpha < 1.0f || color != -1) {
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        stack.pop();
    }

    public static void drawHorizontalGradient(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        Matrix4f matrix = StackHolder.STACK.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferbuilder.vertex(matrix, (float)right, (float)top, 0.0f).color(f1, f2, f3, f);
        bufferbuilder.vertex(matrix, (float)left, (float)top, 0.0f).color(f5, f6, f7, f4);
        bufferbuilder.vertex(matrix, (float)left, (float)bottom, 0.0f).color(f5, f6, f7, f4);
        bufferbuilder.vertex(matrix, (float)right, (float)bottom, 0.0f).color(f1, f2, f3, f);
        BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferbuilder.end());
        RenderSystem.disableBlend();
    }

    public static void drawToolTip(String tooltip, int mouseX, int mouseY) {
        int width = DrawUtils.getStringWidth((String)tooltip);
        MatrixStack stack = StackHolder.STACK;
        stack.push();
        stack.translate(0.0f, 0.0f, 200.0f);
        DrawUtils.drawRoundedRect((int)(mouseX + 2), (int)(mouseY - 3), (int)(mouseX + width + 8), (int)(mouseY + 11), (int)UIConstants.UI_SEPARATION_COLOR);
        DrawUtils.drawRoundedRect((int)(mouseX + 3), (int)(mouseY - 2), (int)(mouseX + width + 7), (int)(mouseY + 10), (int)UIConstants.UI_COMPONENT_COLOR);
        DrawUtils.drawString((String)tooltip, (float)(mouseX + 5), (float)mouseY);
        stack.pop();
    }

    public static void drawDarkOverlay(int width, int height) {
        DrawUtils.drawRect((int)0, (int)0, (int)width, (int)height, (int)-1072689136);
    }

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        Matrix4f matrix = StackHolder.STACK.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)left, (float)bottom, 0.0f).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)right, (float)bottom, 0.0f).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)right, (float)top, 0.0f).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)left, (float)top, 0.0f).color(r, g, b, a);
        BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void drawRoundedRect(int left, int top, int right, int bottom, int color) {
        DrawUtils.drawRoundedRect((int)left, (int)top, (int)right, (int)bottom, (int)color, (int)2);
    }

    public static void drawRoundedWindowRect(int left, int top, int right, int bottom, int color) {
        DrawUtils.drawRoundedRect((int)left, (int)top, (int)right, (int)bottom, (int)color, (int)5);
    }

    public static void drawRoundedRect(int left, int top, int right, int bottom, int color, int rad) {
        DrawUtils.drawRoundedRect((int)left, (int)top, (int)right, (int)bottom, (int)color, (int)rad, (int)36);
    }

    public static void drawRoundedRect(int left, int top, int right, int bottom, int color, int rad, int points) {
        DrawUtils.drawRoundedRectOuter((int)(left + rad), (int)(top + rad), (int)(right - rad), (int)(bottom - rad), (int)color, (int)rad, (int)points);
    }

    public static void drawRoundedRectOuter(int left, int top, int right, int bottom, int color, int rad, int points) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        Matrix4f matrix = StackHolder.STACK.peek().getPositionMatrix();
        DrawUtils.drawCircleQuarter((Matrix4f)matrix, (int)left, (int)top, (int)rad, (int)36, (int)1, (float)r, (float)g, (float)b, (float)a);
        DrawUtils.drawCircleQuarter((Matrix4f)matrix, (int)left, (int)bottom, (int)rad, (int)36, (int)2, (float)r, (float)g, (float)b, (float)a);
        DrawUtils.drawCircleQuarter((Matrix4f)matrix, (int)right, (int)bottom, (int)rad, (int)36, (int)3, (float)r, (float)g, (float)b, (float)a);
        DrawUtils.drawCircleQuarter((Matrix4f)matrix, (int)right, (int)top, (int)rad, (int)36, (int)4, (float)r, (float)g, (float)b, (float)a);
        DrawUtils.drawRectPart((Matrix4f)matrix, (int)left, (int)(top + rad), (int)right, (int)(bottom - rad), (float)r, (float)g, (float)b, (float)a);
        DrawUtils.drawRectPart((Matrix4f)matrix, (int)right, (int)top, (int)(right - rad), (int)bottom, (float)r, (float)g, (float)b, (float)a);
        DrawUtils.drawRectPart((Matrix4f)matrix, (int)(left + rad), (int)top, (int)left, (int)bottom, (float)r, (float)g, (float)b, (float)a);
        RenderSystem.disableBlend();
    }

    private static void drawRectPart(Matrix4f matrix, int left, int top, int right, int bottom, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)left, (float)bottom, 0.0f).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)right, (float)bottom, 0.0f).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)right, (float)top, 0.0f).color(r, g, b, a);
        bufferBuilder.vertex(matrix, (float)left, (float)top, 0.0f).color(r, g, b, a);
        BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferBuilder.end());
    }

    private static void drawCircleQuarter(Matrix4f matrix, int x, int y, int rad, int points, int dir, float r, float g, float b, float a) {
        int quarter = (int)((double)points / 4.0);
        int start = dir * quarter - quarter;
        int end = dir * quarter;
        DrawUtils.drawCirclePart((Matrix4f)matrix, (int)x, (int)y, (int)rad, (int)points, (int)start, (int)end, (float)r, (float)g, (float)b, (float)a);
    }

    private static void drawCirclePart(Matrix4f matrix, int x, int y, int rad, int points, int start, int end, float r, float g, float b, float a) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)x, (float)y, 0.0f).color(r, g, b, a);
        for (int ii = start; ii <= end; ++ii) {
            float theta = 6.283185f * (float)ii / (float)points;
            float cx = (float)rad * MathUtils.cos((float)theta);
            float cy = (float)rad * MathUtils.sin((float)theta);
            bufferBuilder.vertex(matrix, (float)x + cy, (float)y + cx, 0.0f).color(r, g, b, a);
        }
        BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferBuilder.end());
    }

    public static void drawEntryExtension(int x, int y, boolean tileVisible, boolean mouseOnSymbol) {
        DrawUtils.drawCenteredString((String)(tileVisible ? "\u25bc" : "\u25b6"), (float)(x + 6), (float)(y + 6), (int)(mouseOnSymbol || tileVisible ? UIConstants.ELEMENT_SELECTION_COLOR : UIConstants.UI_SEPARATION_COLOR));
    }

    public static void drawEntryBackground(int x, int y, int tileWidth, int slotHeight, boolean tileExtended) {
        DrawUtils.drawRect((int)x, (int)(y + 1), (int)tileWidth, (int)(y + slotHeight - 1), (int)Utils.toRGB((int)20, (int)20, (int)20, (int)50));
        DrawUtils.drawRect((int)x, (int)y, (int)tileWidth, (int)(y + slotHeight), (int)Utils.toRGB((int)20, (int)20, (int)20, (int)150));
        DrawUtils.drawRect((int)x, (int)(y + 1), (int)(x + 1), (int)(y + slotHeight - 1), (int)(tileExtended ? UIConstants.ELEMENT_SELECTION_COLOR : UIConstants.UI_SEPARATION_COLOR));
    }

    public static int drawString(String text, float x, float y, int color) {
        if (customFontRenderer != null) {
            return customFontRenderer.drawString(text, x, y, 0xFF000000 | color, false);
        }
        VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        return DrawUtils.getDefaultFontRenderer().draw(text, x, y, color, true, StackHolder.STACK.peek().getPositionMatrix(), (VertexConsumerProvider)consumerProvider, TextRenderer.TextLayerType.NORMAL, 0, 0xF000F0);
    }

    public static void abstractParentFill(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        int i;
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        boolean z = false;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        float f = (float)ColorHelper.Argb.getAlpha((int)color) / 255.0f;
        float g = (float)ColorHelper.Argb.getRed((int)color) / 255.0f;
        float h = (float)ColorHelper.Argb.getGreen((int)color) / 255.0f;
        float j = (float)ColorHelper.Argb.getBlue((int)color) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, (float)x1, (float)y1, 0.0f).color(g, h, j, f);
        bufferBuilder.vertex(matrix4f, (float)x1, (float)y2, 0.0f).color(g, h, j, f);
        bufferBuilder.vertex(matrix4f, (float)x2, (float)y2, 0.0f).color(g, h, j, f);
        bufferBuilder.vertex(matrix4f, (float)x2, (float)y1, 0.0f).color(g, h, j, f);
        BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static String trimStringToWidth(String text, int width, boolean reverse) {
        if (customFontRenderer != null) {
            return customFontRenderer.trimStringToWidth(text, width, reverse);
        }
        return DrawUtils.getDefaultFontRenderer().trimToWidth(text, width, reverse);
    }

    public static int getStringWidth(String text) {
        if (customFontRenderer != null) {
            return customFontRenderer.getStringWidth(text);
        }
        return DrawUtils.getDefaultFontRenderer().getWidth(text);
    }

    public static CustomFontRenderer getCustomFontRenderer() {
        return customFontRenderer;
    }

    public static void setCustomFontRenderer(CustomFontRenderer customFontRenderer) {
        DrawUtils.customFontRenderer = customFontRenderer;
    }

    public static int drawString(String text, float x, float y) {
        return DrawUtils.drawString((String)text, (float)x, (float)y, (int)UIConstants.UI_TEXT_COLOR);
    }

    public static void drawString(String text, float x, float y, float size) {
        DrawUtils.drawString((String)text, (float)x, (float)y, (float)size, (int)UIConstants.UI_TEXT_COLOR);
    }

    public static void drawString(String text, float x, float y, float size, int color) {
        MatrixStack stack = StackHolder.STACK;
        stack.push();
        stack.scale(size, size, size);
        DrawUtils.drawString((String)text, (float)(x / size), (float)(y / size), (int)color);
        stack.pop();
    }

    public static void drawCenteredString(String text, float x, float y, float size, int color) {
        MatrixStack stack = StackHolder.STACK;
        stack.push();
        stack.scale(size, size, size);
        DrawUtils.drawCenteredString((String)text, (float)(x / size), (float)(y / size), (int)color);
        stack.pop();
    }

    public static void drawCenteredString(String text, float x, float y, float size) {
        DrawUtils.drawCenteredString((String)text, (float)x, (float)y, (float)size, (int)UIConstants.UI_TEXT_COLOR);
    }

    public static int drawCenteredString(String text, float x, float y, int color) {
        return DrawUtils.drawString((String)text, (float)(x - (float)(DrawUtils.getStringWidth((String)text) / 2)), (float)y, (int)color);
    }

    public static int drawCenteredString(String text, float x, float y) {
        return DrawUtils.drawCenteredString((String)text, (float)x, (float)y, (int)UIConstants.UI_TEXT_COLOR);
    }

    public static void drawRawTexture(float x, float y, float imageWidth, float imageHeight, float maxWidth, float maxHeight) {
        MatrixStack stack = StackHolder.STACK;
        stack.push();
        float sizeWidth = maxWidth / imageWidth;
        float sizeHeight = maxHeight / imageHeight;
        stack.scale(sizeWidth, sizeHeight, 0.0f);
        DrawUtils.drawTexturedModalRect((Matrix4f)stack.peek().getPositionMatrix(), (float)(x / sizeWidth), (float)(y / sizeHeight), (float)(x / sizeWidth + imageWidth), (float)(y / sizeHeight + imageHeight));
        stack.pop();
    }

    public static void drawTexturedModalRect(Matrix4f matrix, float left, float top, float right, float bottom) {
        float textureX = 0.0f;
        float textureY = 0.0f;
        float width = right - left;
        float height = bottom - top;
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, left, top + height, 0.0f).texture(textureX * f, (textureY + height) * f1);
        bufferBuilder.vertex(matrix, left + width, top + height, 0.0f).texture((textureX + width) * f, (textureY + height) * f1);
        bufferBuilder.vertex(matrix, left + width, top, 0.0f).texture((textureX + width) * f, textureY * f1);
        bufferBuilder.vertex(matrix, left, top, 0.0f).texture(textureX * f, textureY * f1);
        BufferRenderer.drawWithGlobalProgram((BuiltBuffer)bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void drawTextureWithLight(float x, float y, float imageWidth, float imageHeight, float maxWidth, float maxHeight, int color, int light, Identifier id, VertexConsumerProvider provider) {
        float sizeWidth = maxWidth / imageWidth;
        float sizeHeight = maxHeight / imageHeight;
        MatrixStack stack = StackHolder.STACK;
        stack.push();
        stack.scale(sizeWidth, sizeHeight, 0.0f);
        int overlay = OverlayTexture.DEFAULT_UV;
        float left = x / sizeWidth;
        float top = y / sizeHeight;
        float right = x / sizeWidth + imageWidth;
        float bottom = y / sizeHeight + imageHeight;
        float textureX = 0.0f;
        float textureY = 0.0f;
        float width = right - left;
        float height = bottom - top;
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        RenderSystem.setShaderTexture((int)0, (Identifier)id);
        RenderSystem.setShaderColor((float)red, (float)green, (float)blue, (float)alpha);
        RenderSystem.enableBlend();
        VertexConsumer bufferBuilder = provider.getBuffer(RenderLayer.getEntityCutout((Identifier)id));
        Matrix4f matrix = StackHolder.STACK.peek().getPositionMatrix();
        bufferBuilder.vertex(matrix, left, top + height, 0.0f).color(red, green, blue, alpha).texture(textureX * f, (textureY + height) * f1).overlay(overlay).light(light).normal(0.0f, 0.0f, 1.0f);
        bufferBuilder.vertex(matrix, left + width, top + height, 0.0f).color(red, green, blue, alpha).texture((textureX + width) * f, (textureY + height) * f1).overlay(overlay).light(light).normal(0.0f, 0.0f, 1.0f);
        bufferBuilder.vertex(matrix, left + width, top, 0.0f).color(red, green, blue, alpha).texture((textureX + width) * f, textureY * f1).overlay(overlay).light(light).normal(0.0f, 0.0f, 1.0f);
        bufferBuilder.vertex(matrix, left, top, 0.0f).color(red, green, blue, alpha).texture(textureX * f, textureY * f1).overlay(overlay).light(light).normal(0.0f, 0.0f, 1.0f);
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        stack.pop();
    }

    public static void drawDebugAxis(float size) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)size);
        GL11.glBegin((int)2);
        GL11.glColor3f((float)0.0f, (float)255.0f, (float)0.0f);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)1.0, (double)0.0);
        GL11.glColor3f((float)0.0f, (float)0.0f, (float)255.0f);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)-1.0, (double)0.0, (double)0.0);
        GL11.glColor3f((float)255.0f, (float)0.0f, (float)0.0f);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)-1.0);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawEntityOnScreen(int x, int y, int size, float mouseX, float mouseY, int rotationX, int rotationY, int rotationZ, LivingEntity entity) {
        StackHolder stack = StackHolder.getInstance();
        stack.push();
        stack.translate((float)x, (float)y, 100.0f);
        stack.scale((float)(-size) - 30.0f, (float)size + 30.0f, (float)size);
        stack.rotateZ(180.0f);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        stack.rotateY(135.0f);
        stack.rotateY((float)(-135 + rotationX));
        stack.rotateX((float)rotationY);
        stack.rotateZ((float)rotationZ);
        stack.rotateX(-((float)Math.atan(mouseY / 40.0f)) * 20.0f);
        stack.translate(0.0f, 0.0f, 0.0f);
        entity.bodyYaw = (float)Math.atan(mouseX / 40.0f) * 20.0f;
        entity.setYaw((float)Math.atan(mouseX / 40.0f) * 40.0f);
        entity.setPitch(-((float)Math.atan(mouseY / 40.0f)) * 20.0f);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        DiffuseLighting.disableGuiDepthLighting();
        EntityRenderDispatcher renderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();
        Quaternionf quaternion = QuaternionHelper.getQuaternion((float)0.0f, (float)180.0f, (float)0.0f, (boolean)true);
        renderManager.setRotation(quaternion);
        renderManager.setRenderShadows(false);
        VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> DrawUtils.synth_drawEntityOnScreen_0(renderManager, entity, rotationY, (RenderStack)stack, consumerProvider));
        consumerProvider.draw();
        renderManager.setRenderShadows(true);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        DiffuseLighting.enableGuiDepthLighting();
        stack.pop();
    }

    public static void renderCosmeticPreview(CosmeticModel model, int x, int y, float size, float rotX, float rotY, float rotZ, boolean background) {
        StackHolder stack = StackHolder.getInstance();
        stack.push();
        stack.translate((float)x, (float)y, 100.0f);
        stack.scale(-size - 30.0f, size + 30.0f, size);
        stack.rotateZ(180.0f);
        stack.rotateY(135.0f);
        stack.rotate(rotY, -135.0f + rotX, rotZ);
        stack.translate(0.0f, 0.0f, 0.0f);
        float scale = background ? 1.0f : 0.5f;
        stack.scale(-scale, -scale, scale);
        scale = model.getPreviewScale();
        stack.translate(-0.05f, model.getPreviewY(), 0.0f);
        stack.scale(scale, scale, scale);
        float[] rot = model.getPreviewRot();
        if (rot != null) {
            stack.rotateX(rot[0]);
            stack.rotateY(rot[1]);
            stack.rotateZ(rot[2]);
        }
        DiffuseLighting.disableGuiDepthLighting();
        MinecraftClient mc = MinecraftClient.getInstance();
        MoreCosmetics mod = MoreCosmetics.getInstance();
        ModelData data = (ModelData)mod.getUserHandler().getCurrentUser().getCosmetics().get(model.getId());
        data = data == null ? mod.getUserHandler().loadData(model, true) : data;
        ModelCosmeticRenderer renderer = (ModelCosmeticRenderer)mod.getModelHandler();
        VertexConsumerProvider.Immediate consumerProvider = mc.getBufferBuilders().getEntityVertexConsumers();
        renderer.renderCosmetic((MatrixStack)stack.get(), (VertexConsumerProvider)consumerProvider, 0xF000F0, (AbstractClientPlayerEntity)mc.player, model, data, 0.0f, 0.0f, SharedVars.RENDER_TICKS, 0.0f, 0.0f, 0.0f, null, true, false);
        consumerProvider.draw();
        DiffuseLighting.enableGuiDepthLighting();
        stack.pop();
    }

    public static int getFontHeight() {
        Objects.requireNonNull(DrawUtils.getDefaultFontRenderer());
        return 9;
    }

    public static TextRenderer getDefaultFontRenderer() {
        return MinecraftClient.getInstance().textRenderer;
    }

    private static /* synthetic */ void synth_drawEntityOnScreen_0(EntityRenderDispatcher renderManager, LivingEntity entity, int rotationY, RenderStack stack, VertexConsumerProvider.Immediate consumerProvider) {
        renderManager.render((Entity)entity, 0.0, rotationY == 0 ? 0.0 : -1.0, 0.0, 0.0f, 1.0f, (MatrixStack)stack.get(), (VertexConsumerProvider)consumerProvider, 0xF00000);
    }
}

