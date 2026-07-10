/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoCube
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoQuad
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoVertex
 *  com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 *  com.cosmeticsmod.morecosmetics.models.model.AnimationModel
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.SubModel
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  com.cosmeticsmod.morecosmetics.utils.RainbowHandler
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.entity.model.PlayerEntityModel
 *  org.joml.Matrix3fc
 *  org.joml.Matrix4fc
 *  org.joml.Vector3f
 *  org.joml.Vector4f
 *  v1_21.morecosmetics.models.renderer.GeoModelRenderer
 *  v1_21.morecosmetics.models.renderer.MatrixStackWrapper
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 *  v1_21.morecosmetics.models.renderer.StackHolder
 */
package v1_21.morecosmetics.models.renderer;

import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoCube;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoQuad;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoVertex;
import com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.config.SettingOverlay;
import com.cosmeticsmod.morecosmetics.models.model.AnimationModel;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.SubModel;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import com.cosmeticsmod.morecosmetics.utils.RainbowHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.joml.Matrix3fc;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import v1_21.morecosmetics.models.renderer.MatrixStackWrapper;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;
import v1_21.morecosmetics.models.renderer.StackHolder;

@Environment(value=EnvType.CLIENT)
public class GeoModelRenderer {
    private static final List<Object> EXTRA_DATA = new ArrayList();
    public static MatrixStackWrapper MATRIX_STACK = new MatrixStackWrapper();
    private float tickValue;
    private float motionAdd;
    private float motionSub;
    private float rotation;
    private ModelCosmeticRenderer renderer;
    private PlayerEntityModel modelPlayer;
    private ModelData data;
    private CosmeticModel model;
    private SubModel[] subModels;
    private boolean slim;

    private static class MutableAnimationEvent<T extends IAnimatable> extends AnimationEvent<T> {
        private T animatable;
        private float limbSwing;
        private float limbSwingAmount;
        private float partialTick;
        private boolean isMoving;
        private List<Object> extraData;

        public MutableAnimationEvent() {
            super(null, 0.0f, 0.0f, 0.0f, false, null);
        }

        public void set(T animatable, float limbSwing, float limbSwingAmount, float partialTick, boolean isMoving, List<Object> extraData) {
            this.animatable = animatable;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.partialTick = partialTick;
            this.isMoving = isMoving;
            this.extraData = extraData;
            this.animationTick = 0.0;
            this.controller = null;
        }

        @Override public T getAnimatable() { return this.animatable; }
        @Override public float getLimbSwing() { return this.limbSwing; }
        @Override public float getLimbSwingAmount() { return this.limbSwingAmount; }
        @Override public float getPartialTick() { return this.partialTick; }
        @Override public boolean isMoving() { return this.isMoving; }
        @Override public List<Object> getExtraData() { return this.extraData; }
    }

    private final MutableAnimationEvent reusableEvent = new MutableAnimationEvent();

    public void updateAnimation(Integer id, CosmeticAnimatable animatable, float partialTicks) {
        reusableEvent.set((IAnimatable)animatable, 0.0f, 0.0f, partialTicks, false, EXTRA_DATA);
        animatable.getModel().setLivingAnimations((IAnimatable)animatable, id, reusableEvent);
    }

    public void renderModel(CosmeticModel model, VertexConsumerProvider provider, VertexConsumer builder, ModelCosmeticRenderer renderer, SubModel[] subModels, ModelData data, PlayerEntityModel modelPlayer, boolean slim, int overlay, int light, float tickValue, float motionAdd, float motionSub, float rotation) {
        this.renderer = renderer;
        this.subModels = subModels;
        this.data = data;
        this.model = model;
        this.modelPlayer = modelPlayer;
        this.slim = slim;
        this.tickValue = tickValue;
        this.motionAdd = motionAdd;
        this.motionSub = motionSub;
        this.rotation = rotation;
        MATRIX_STACK.update(StackHolder.STACK);
        RenderSystem.disableCull();
        for (GeoBone group : model.getModel().topLevelBones) {
            this.renderRecursively(group, provider, builder, overlay, light, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        RenderSystem.enableCull();
    }

    private void renderRecursively(GeoBone bone, VertexConsumerProvider provider, VertexConsumer builder, int overlay, int light, float red, float green, float blue, float alpha) {
        int i = bone.subModelIndex;
        if (i != -1) {
            int color;
            SettingOverlay subData;
            SubModel subModel = this.subModels[i];
            SettingOverlay settingOverlay = subData = this.data.model[i] != null ? this.data.model[i] : ModelCosmeticRenderer.PLACEHOLDER_DATA;
            if (!subModel.isVisible() || !subData.visible) {
                return;
            }
            RenderStack stack = ModelCosmeticRenderer.STACK;
            stack.push();
            float y = this.renderer.transformToEntity((PositionModel)subModel, this.modelPlayer, true, this.slim, stack);
            this.renderer.transformModel((PositionModel)subModel, y, subData, stack);
            this.renderer.appendAnimations((AnimationModel)subModel, this.tickValue, this.motionAdd, this.motionSub, this.rotation, subData, stack);
            if (subModel.hasTextures()) {
                this.renderer.renderTextureModels(subModel.getTextures(), this.data, null, false, this.tickValue, this.motionAdd, this.motionSub, this.rotation, provider, light, this.model.getName(), false);
                Identifier loc = (Identifier)ModelCosmeticRenderer.RESLOCS.get(this.model.getTextureName());
                MinecraftClient.getInstance().getTextureManager().bindTexture(loc);
                RenderSystem.setShaderTexture((int)0, (Identifier)loc);
                builder = provider.getBuffer(RenderLayer.getEntityTranslucent((Identifier)loc));
            }
            int n = color = subData.color != 0 ? subData.color : subModel.getColor();
            if (color == 1) {
                color = RainbowHandler.RAINBOW_VALUE;
            }
            red = (float)(color >> 16 & 0xFF) / 255.0f;
            green = (float)(color >> 8 & 0xFF) / 255.0f;
            blue = (float)(color & 0xFF) / 255.0f;
            alpha = (float)(color >> 24 & 0xFF) / 255.0f;
            RenderSystem.setShaderColor((float)red, (float)green, (float)blue, (float)alpha);
            if (subData.illum) {
                light = 0xF000F0;
            }
        }
        MATRIX_STACK.push();
        MATRIX_STACK.translate(bone);
        MATRIX_STACK.moveToPivot(bone);
        MATRIX_STACK.rotate(bone);
        MATRIX_STACK.scale(bone);
        MATRIX_STACK.moveBackFromPivot(bone);
        if (!bone.isHidden) {
            for (GeoCube cube : bone.childCubes) {
                MATRIX_STACK.push();
                this.renderCube(builder, cube, overlay, light, red, green, blue, alpha);
                MATRIX_STACK.pop();
            }
            for (GeoBone childBone : bone.childBones) {
                this.renderRecursively(childBone, provider, builder, overlay, light, red, green, blue, alpha);
            }
        }
        MATRIX_STACK.pop();
        if (i != -1) {
            ModelCosmeticRenderer.STACK.pop();
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    private void renderCube(VertexConsumer builder, GeoCube cube, int overlay, int light, float red, float green, float blue, float alpha) {
        MATRIX_STACK.moveToPivot(cube);
        MATRIX_STACK.rotate(cube);
        MATRIX_STACK.moveBackFromPivot(cube);
        for (GeoQuad quad : cube.quads) {
            Vector3f normal = new Vector3f((float)quad.normal.getX(), (float)quad.normal.getY(), (float)quad.normal.getZ());
            normal.mul((Matrix3fc)MATRIX_STACK.getStack().peek().getNormalMatrix());
            if ((cube.size.getY() == 0.0f || cube.size.getZ() == 0.0f) && normal.x() < 0.0f) {
                normal.mul(-1.0f, 1.0f, 1.0f);
            }
            if ((cube.size.getX() == 0.0f || cube.size.getZ() == 0.0f) && normal.y() < 0.0f) {
                normal.mul(1.0f, -1.0f, 1.0f);
            }
            if ((cube.size.getX() == 0.0f || cube.size.getY() == 0.0f) && normal.z() < 0.0f) {
                normal.mul(1.0f, 1.0f, -1.0f);
            }
            for (GeoVertex vertex : quad.vertices) {
                Vector4f vector4f = new Vector4f(vertex.position.getX(), vertex.position.getY(), vertex.position.getZ(), 1.0f);
                vector4f.mul((Matrix4fc)MATRIX_STACK.getStack().peek().getPositionMatrix());
                builder.vertex(vector4f.x(), vector4f.y(), vector4f.z()).color(red, green, blue, alpha).texture(vertex.textureU, vertex.textureV).overlay(overlay).light(light).normal(normal.x(), normal.y(), normal.z());
            }
        }
    }
}

