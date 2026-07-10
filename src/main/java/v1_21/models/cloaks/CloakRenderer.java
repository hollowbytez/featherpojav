/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.player.PlayerModelPart
 *  net.minecraft.item.Items
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.OverlayTexture
 *  net.minecraft.client.model.Dilation
 *  net.minecraft.client.model.ModelPartBuilder
 *  net.minecraft.client.model.ModelPart
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  org.joml.Vector3f
 *  v1_21.morecosmetics.models.cloaks.CloakRenderer
 *  v1_21.morecosmetics.models.renderer.QuaternionHelper
 */
package v1_21.morecosmetics.models.cloaks;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Items;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.joml.Vector3f;
import v1_21.morecosmetics.models.renderer.QuaternionHelper;

@Environment(value=EnvType.CLIENT)
public class CloakRenderer {
    private final ModelPart cloakModel;
    private float capeRotX;
    private float capeRotY;
    private float capeRotZ;

    public CloakRenderer() {
        ModelPartBuilder builder = ModelPartBuilder.create().uv(0, 0).cuboid(-5.0f, 0.0f, -1.0f, 10.0f, 16.0f, 1.0f, Dilation.NONE, 1.0f, 1.0f);
        List list = (List)builder.build().stream().map(modelCuboidData -> modelCuboidData.createCuboid(22, 17)).collect(ImmutableList.toImmutableList());
        this.cloakModel = new ModelPart(list, Collections.emptyMap());
    }

    public boolean render(MatrixStack stack, AbstractClientPlayerEntity entity, Identifier loc, float partialTicks, VertexConsumerProvider provider, int light) {
        if (loc == null || entity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA) || !entity.isPartVisible(PlayerModelPart.CAPE)) {
            return false;
        }
        stack.push();
        stack.translate(0.0, 0.0, 0.125);
        float h = partialTicks;
        double d = MathHelper.lerp((double)h, (double)entity.prevCapeX, (double)entity.capeX) - MathHelper.lerp((double)h, (double)entity.prevX, (double)entity.getX());
        double e = MathHelper.lerp((double)h, (double)entity.prevCapeY, (double)entity.capeY) - MathHelper.lerp((double)h, (double)entity.prevY, (double)entity.getY());
        double m = MathHelper.lerp((double)h, (double)entity.prevCapeZ, (double)entity.capeZ) - MathHelper.lerp((double)h, (double)entity.prevZ, (double)entity.getZ());
        float n = entity.prevBodyYaw + (entity.bodyYaw - entity.prevBodyYaw);
        double o = MathHelper.sin((float)(n * ((float)Math.PI / 180)));
        double p = -MathHelper.cos((float)(n * ((float)Math.PI / 180)));
        float q = (float)e * 10.0f;
        q = MathHelper.clamp((float)q, (float)-6.0f, (float)32.0f);
        float r = (float)(d * o + m * p) * 100.0f;
        r = MathHelper.clamp((float)r, (float)0.0f, (float)150.0f);
        float s = (float)(d * p - m * o) * 100.0f;
        s = MathHelper.clamp((float)s, (float)-20.0f, (float)20.0f);
        if (r < 0.0f) {
            r = 0.0f;
        }
        float t = MathHelper.lerp((float)h, (float)entity.prevStrideDistance, (float)entity.strideDistance);
        q += MathHelper.sin((float)(MathHelper.lerp((float)h, (float)entity.prevHorizontalSpeed, (float)entity.horizontalSpeed) * 6.0f)) * 32.0f * t;
        if (entity.isInSneakingPose()) {
            q += 25.0f;
        }
        float cx = MathHelper.lerp((float)h, (float)this.capeRotX, (float)(6.0f + r / 2.0f + q));
        float cz = MathHelper.lerp((float)h, (float)this.capeRotZ, (float)(s / 2.0f));
        float cy = MathHelper.lerp((float)h, (float)this.capeRotY, (float)(180.0f - s / 2.0f));
        this.capeRotX = cx;
        stack.multiply(QuaternionHelper.getDegreesQuaternion((Vector3f)QuaternionHelper.POSITIVE_X, (float)this.capeRotX));
        this.capeRotZ = cz;
        stack.multiply(QuaternionHelper.getDegreesQuaternion((Vector3f)QuaternionHelper.POSITIVE_Z, (float)this.capeRotZ));
        this.capeRotY = cy;
        stack.multiply(QuaternionHelper.getDegreesQuaternion((Vector3f)QuaternionHelper.POSITIVE_Y, (float)this.capeRotY));
        MinecraftClient.getInstance().getTextureManager().bindTexture(loc);
        RenderSystem.setShaderTexture((int)0, (Identifier)loc);
        VertexConsumer vertexConsumer = provider.getBuffer(RenderLayer.getEntityTranslucent((Identifier)loc));
        this.updateAngles(entity);
        this.cloakModel.render(stack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        stack.pop();
        return true;
    }

    public void updateAngles(AbstractClientPlayerEntity entity) {
        if (entity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
            if (entity.isInSneakingPose()) {
                this.cloakModel.pivotZ = 1.4f;
                this.cloakModel.pivotY = 1.85f;
            } else {
                this.cloakModel.pivotZ = 0.0f;
                this.cloakModel.pivotY = 0.0f;
            }
        } else if (entity.isInSneakingPose()) {
            this.cloakModel.pivotZ = 0.3f;
            this.cloakModel.pivotY = 0.8f;
        } else {
            this.cloakModel.pivotZ = -1.1f;
            this.cloakModel.pivotY = -0.85f;
        }
    }
}

