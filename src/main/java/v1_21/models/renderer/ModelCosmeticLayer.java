/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.render.entity.PlayerEntityRenderer
 *  net.minecraft.client.render.entity.feature.FeatureRendererContext
 *  net.minecraft.client.render.entity.feature.FeatureRenderer
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.entity.model.PlayerEntityModel
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticLayer
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 *  v1_21.morecosmetics.models.renderer.StackHolder
 */
package v1_21.morecosmetics.models.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;
import v1_21.morecosmetics.models.renderer.StackHolder;

@Environment(value=EnvType.CLIENT)
public class ModelCosmeticLayer
extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private ModelCosmeticRenderer renderer;
    private PlayerEntityRenderer modelPlayer;
    private boolean slim;

    public ModelCosmeticLayer(PlayerEntityRenderer modelPlayer, ModelCosmeticRenderer renderer, boolean slim) {
        super((FeatureRendererContext)modelPlayer);
        this.modelPlayer = modelPlayer;
        this.renderer = renderer;
        this.slim = slim;
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        StackHolder.update((MatrixStack)matrices);
        this.renderer.renderCosmetics(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, (PlayerEntityModel)this.modelPlayer.getModel(), this.slim, tickDelta);
    }
}

