/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  com.cosmeticsmod.morecosmetics.nametags.EnumNametag
 *  com.cosmeticsmod.morecosmetics.nametags.Nametag
 *  com.cosmeticsmod.morecosmetics.nametags.NametagHandler
 *  com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontData
 *  com.cosmeticsmod.morecosmetics.nametags.font.FontImage
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.utils.CompatibilityManager
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.RainbowHandler
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.text.Text
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.font.TextRenderer
 *  net.minecraft.client.font.TextRenderer$class_6415
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.VertexConsumerProvider$class_4598
 *  net.minecraft.text.StringVisitable
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  net.minecraft.client.network.ClientPlayerEntity
 *  net.minecraft.scoreboard.ScoreboardDisplaySlot
 *  org.joml.Matrix4f
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 *  v1_21.morecosmetics.nametags.FontImageRenderer
 *  v1_21.morecosmetics.nametags.NametagRenderer
 */
package v1_21.morecosmetics.nametags;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import com.cosmeticsmod.morecosmetics.nametags.EnumNametag;
import com.cosmeticsmod.morecosmetics.nametags.Nametag;
import com.cosmeticsmod.morecosmetics.nametags.NametagHandler;
import com.cosmeticsmod.morecosmetics.nametags.font.CustomFontRenderer;
import com.cosmeticsmod.morecosmetics.nametags.font.FontData;
import com.cosmeticsmod.morecosmetics.nametags.font.FontImage;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.utils.CompatibilityManager;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.RainbowHandler;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.StringVisitable;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import org.joml.Matrix4f;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomTextureManager;
import v1_21.morecosmetics.nametags.FontImageRenderer;

@Environment(value=EnvType.CLIENT)
public class NametagRenderer
extends NametagHandler {
    public static final RenderStack<MatrixStack> STACK = StackHolder.getInstance();
    private final CustomTextureManager ctm = CustomTextureManager.getGlobalInstance();
    private TextRenderer textRenderer;
    private MinecraftClient mc;
    private static boolean layerRegistered;

    public FontImage addFont(Integer id, FontData fontData) {
        FontImageRenderer image = new FontImageRenderer(fontData, this.colorCode);
        this.fontRendererMap.put(id, image);
        return image;
    }

    public void resetLogo(String id) {
        this.ctm.getImageLocations().remove(id);
    }

    public void renderNametag(Object stack, Object entity, double x, double y, double z) {
        if (entity instanceof AbstractClientPlayerEntity) {
            if (!layerRegistered) {
                layerRegistered = true;
                MoreCosmetics.getInstance().getModelHandler().registerLayer();
            }
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)entity;
            this.renderPlayerNametag((MatrixStack)stack, (Entity)player, x, y, z, SharedVars.PARTIAL_TICKS);
        }
    }

    public void renderPlayerNametag(MatrixStack matrixStack, Entity entity, double x, double y, double z, float partialTicks) {
        boolean hasNametag;
        StackHolder.update((MatrixStack)matrixStack);
        if (!MinecraftClient.isHudEnabled()) {
            return;
        }
        this.mc = MinecraftClient.getInstance();
        this.textRenderer = this.mc.textRenderer;
        CosmeticUser cosmeticUser = this.userHandler.getUser(entity.getUuid());
        boolean showName = CompatibilityManager.shouldShowName() && ModConfig.getConfig().showName && entity == this.mc.player;
        boolean bl = hasNametag = NametagHandler.isNametagEnabled() && ModConfig.getConfig().nametags && cosmeticUser != null && cosmeticUser.hasNametag();
        if (showName || hasNametag) {
            ClientPlayerEntity living = this.mc.player;
            if (living == null) {
                return;
            }
            double distance = entity.distanceTo((Entity)living);
            if (distance <= 2500.0 && !entity.isInvisibleTo((PlayerEntity)this.mc.player)) {
                y = 0.25;
                if (distance < 100.0 && this.mc.world.getScoreboard().getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME) != null) {
                    Objects.requireNonNull(this.textRenderer);
                    y += (double)(9.0f * 1.15f * 0.02666667f);
                }
                if (cosmeticUser != null) {
                    y += (double)cosmeticUser.getNametagHeight();
                }
                if (showName) {
                    this.renderName(entity, 0.0, y, 0.0, partialTicks == 1.0f);
                }
                if (hasNametag && !entity.isSneaking()) {
                    this.renderNametag(entity, cosmeticUser.getNametag(), 0.0, y, 0.0, partialTicks == 1.0f);
                }
            }
        }
    }

    private void renderName(Entity entity, double x, double y, double z, boolean gui) {
        boolean sneaking = entity.isSneaking();
        STACK.push();
        STACK.translate((float)x, (float)y + entity.getHeight() + 0.25f, (float)z);
        ((MatrixStack)STACK.get()).multiply(this.mc.getEntityRenderDispatcher().getRotation());
        STACK.scale(gui ? -0.025f : 0.025f, -0.025f, 0.025f);
        float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int j = (int)(g * 255.0f) << 24;
        Text text = entity.getDisplayName();
        float h = -this.textRenderer.getWidth((StringVisitable)text) / 2;
        Matrix4f matrix4f = ((MatrixStack)STACK.get()).peek().getPositionMatrix();
        VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        this.textRenderer.draw(text, h, 0.0f, 0x20FFFFFF, false, matrix4f, (VertexConsumerProvider)consumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, j, SharedVars.LIGHT);
        if (!sneaking) {
            this.textRenderer.draw(text, h, 0.0f, -1, false, matrix4f, (VertexConsumerProvider)consumerProvider, TextRenderer.TextLayerType.NORMAL, 0, SharedVars.LIGHT);
        }
        STACK.pop();
    }

    private void renderNametag(Entity entity, Nametag tag, double x, double y, double z, boolean gui) {
        String nametag = "\u00a7f" + tag.getTag();
        float scale = (float)tag.getScale();
        STACK.push();
        STACK.translate(0.0f, (float)y + entity.getHeight() + 0.5f, 0.0f);
        if (scale != 1.0f) {
            float yTrans = Math.abs(1.0 - (double)scale) == 0.25 ? 0.075f : 0.1f;
            STACK.translate(0.0f, scale > 1.0f ? yTrans : -yTrans, 0.0f);
            STACK.scale(scale, scale, scale);
        }
        ((MatrixStack)STACK.get()).multiply(this.mc.getEntityRenderDispatcher().getRotation());
        STACK.scale(gui ? -0.025f : 0.025f, -0.025f, 0.025f);
        if (tag.getMode() == EnumNametag.SWITCHING && tag.hasSecondTag() && System.currentTimeMillis() % 2000L > 1000L) {
            nametag = "\u00a7f" + tag.getSecondTag();
        }
        CustomFontRenderer renderer = null;
        int pos = 0;
        if (tag.hasFont() && this.fontRendererMap.containsKey(tag.getFont())) {
            renderer = (CustomFontRenderer)this.fontRendererMap.get(tag.getFont());
            pos = renderer.getStringWidth(nametag) / 2;
        } else {
            pos = this.textRenderer.getWidth(nametag) / 2;
        }
        if (tag.hasLogo()) {
            this.renderLogo(tag.getLogoURL(), pos);
        }
        this.drawLine(nametag, 0, renderer, pos, true, RainbowHandler.RAINBOW_VALUE);
        if (tag.getMode() == EnumNametag.DOUBLE && tag.hasSecondTag()) {
            String stag = "\u00a7f" + tag.getSecondTag();
            pos = renderer != null ? renderer.getStringWidth(stag) / 2 : this.textRenderer.getWidth(stag) / 2;
            this.drawLine(stag, -10, renderer, pos, true, RainbowHandler.RAINBOW_VALUE);
        }
        STACK.pop();
    }

    private void renderLogo(String url, int pos) {
        Identifier loc = this.ctm.getTexture(url, url);
        if (loc == null) {
            return;
        }
        this.mc.getTextureManager().bindTexture(loc);
        VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        DrawUtils.drawTextureWithLight((float)(-pos - 10), (float)-0.5f, (float)255.0f, (float)255.0f, (float)8.0f, (float)8.0f, (int)-1, (int)SharedVars.LIGHT, (Identifier)loc, (VertexConsumerProvider)consumerProvider);
    }

    private void drawLine(String nametag, int height, CustomFontRenderer renderer, int pos, boolean shadow, int color) {
        STACK.push();
        STACK.translate(0.0f, (float)height, 0.0f);
        if (shadow) {
            RenderSystem.disableDepthTest();
            DrawUtils.abstractParentFill((MatrixStack)((MatrixStack)STACK.get()), (int)(-pos - 1), (int)8, (int)(pos + 1), (int)-1, (int)0x3F000000);
            RenderSystem.enableDepthTest();
        }
        if (renderer != null) {
            this.drawString(renderer, nametag, color);
        } else {
            Matrix4f matrix4f = ((MatrixStack)STACK.get()).peek().getPositionMatrix();
            VertexConsumerProvider.Immediate consumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
            this.textRenderer.draw(nametag, (float)(-this.textRenderer.getWidth(nametag)) / 2.0f, 0.0f, RainbowHandler.RAINBOW_VALUE, false, matrix4f, (VertexConsumerProvider)consumerProvider, TextRenderer.TextLayerType.NORMAL, 0, SharedVars.LIGHT);
        }
        STACK.pop();
    }

    private void drawString(CustomFontRenderer renderer, String text, int color) {
        ((FontImageRenderer)renderer).enableLightning(SharedVars.LIGHT);
        renderer.renderString(text, (float)(-renderer.getStringWidth(text)) / 2.0f, 0.0f, color, false);
        ((FontImageRenderer)renderer).disableLightning();
    }
}

