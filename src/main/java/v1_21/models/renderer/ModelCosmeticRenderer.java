/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.models.ModelHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.animated.AnimationEventType
 *  com.cosmeticsmod.morecosmetics.models.animation.Animation
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis
 *  com.cosmeticsmod.morecosmetics.models.animation.AnimationType
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.config.SettingOverlay
 *  com.cosmeticsmod.morecosmetics.models.model.AnimationModel
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.model.ItemModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.TextureModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback
 *  com.cosmeticsmod.morecosmetics.models.renderer.RenderStack
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  com.cosmeticsmod.morecosmetics.utils.ModConfig
 *  com.cosmeticsmod.morecosmetics.utils.SharedVars
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.render.entity.PlayerEntityRenderer
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.client.render.RenderLayer
 *  net.minecraft.client.render.BufferBuilder
 *  net.minecraft.client.render.Tessellator
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.client.render.VertexFormat$class_5596
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.resource.ResourceManager
 *  net.minecraft.resource.ResourceReloader
 *  net.minecraft.resource.ResourceReloader$class_4045
 *  net.minecraft.resource.ReloadableResourceManagerImpl
 *  net.minecraft.util.profiler.Profiler
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.client.render.VertexConsumer
 *  net.minecraft.client.render.VertexConsumerProvider
 *  net.minecraft.client.render.OverlayTexture
 *  net.minecraft.client.render.entity.model.PlayerEntityModel
 *  net.minecraft.client.model.ModelPart
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  net.minecraft.client.render.GameRenderer
 *  net.minecraft.client.render.model.json.ModelTransformationMode
 *  net.minecraft.client.util.SkinTextures$class_7920
 *  net.minecraft.client.render.entity.LivingEntityRenderer
 *  v1_21.morecosmetics.DrawUtils
 *  v1_21.morecosmetics.compatibility.mixin.accessor.FeatureAccessor
 *  v1_21.morecosmetics.compatibility.mixin.accessor.RenderDispatcherAccessor
 *  v1_21.morecosmetics.models.renderer.GeoModelRenderer
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticLayer
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer
 *  v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer$1
 *  v1_21.morecosmetics.models.renderer.StackHolder
 *  v1_21.morecosmetics.models.textures.CustomImage
 *  v1_21.morecosmetics.models.textures.CustomTextureManager
 */
package v1_21.morecosmetics.models.renderer;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.models.ModelHandler;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.animated.AnimationEventType;
import com.cosmeticsmod.morecosmetics.models.animation.Animation;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationAxis;
import com.cosmeticsmod.morecosmetics.models.animation.AnimationType;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.config.SettingOverlay;
import com.cosmeticsmod.morecosmetics.models.model.AnimationModel;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.models.model.ItemModel;
import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.TextureModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderCallback;
import com.cosmeticsmod.morecosmetics.models.renderer.RenderStack;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import com.cosmeticsmod.morecosmetics.utils.ModConfig;
import com.cosmeticsmod.morecosmetics.utils.SharedVars;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import v1_21.morecosmetics.DrawUtils;
import v1_21.morecosmetics.compatibility.mixin.accessor.FeatureAccessor;
import v1_21.morecosmetics.compatibility.mixin.accessor.RenderDispatcherAccessor;
import v1_21.morecosmetics.models.renderer.GeoModelRenderer;
import v1_21.morecosmetics.models.renderer.ModelCosmeticLayer;
import v1_21.morecosmetics.models.renderer.ModelCosmeticRenderer;
import v1_21.morecosmetics.models.renderer.StackHolder;
import v1_21.morecosmetics.models.textures.CustomImage;
import v1_21.morecosmetics.models.textures.CustomTextureManager;

@Environment(value=EnvType.CLIENT)
public class ModelCosmeticRenderer
extends ModelHandler
implements ResourceReloader {
    public static final RenderStack<MatrixStack> STACK = StackHolder.getInstance();
    public static final float SCALE = 0.0625f;
    public static final HashMap<Integer, ItemStack> ITEMS = new HashMap();
    public static final HashMap<String, Identifier> RESLOCS = new HashMap();
    public static final SettingOverlay PLACEHOLDER_DATA = new SettingOverlay();
    public static Identifier NEXT_CAPE_LOC;
    private final CustomTextureManager ctm = CustomTextureManager.getGlobalInstance();
    private final GeoModelRenderer geoModelRenderer = new GeoModelRenderer();

    public ModelCosmeticRenderer() {
        ((ReloadableResourceManagerImpl)MinecraftClient.getInstance().getResourceManager()).registerReloader((ResourceReloader)this);
    }

    public CompletableFuture<Void> reload(ResourceReloader.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return ((CompletableFuture)CompletableFuture.runAsync(() -> MoreCosmetics.log((String)"Detected resource reload!"), applyExecutor).thenCompose(arg_0 -> ((ResourceReloader.Synchronizer)synchronizer).whenPrepared(arg_0))).thenRun(() -> this.registerLayer());
    }

    public void registerLayer() {
        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            for (SkinTextures.Model key : ((RenderDispatcherAccessor)mc.getEntityRenderDispatcher()).getModelRenderers().keySet()) {
                PlayerEntityRenderer playerRenderer = (PlayerEntityRenderer)((RenderDispatcherAccessor)mc.getEntityRenderDispatcher()).getModelRenderers().get(key);
                List layerRenderers = ((FeatureAccessor)playerRenderer).getFeatures();
                layerRenderers.add(0, new ModelCosmeticLayer(playerRenderer, this, key == SkinTextures.Model.SLIM));
                this.renderLayers.put(key.getName(), layerRenderers);
            }
        }
        catch (Throwable e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }

    public float renderCosmetics(MatrixStack stack, VertexConsumerProvider provider, int light, AbstractClientPlayerEntity entity, float movementFactor, float walkingSpeed, float tickValue, PlayerEntityModel modelPlayer, boolean slim, float partialTicks) {
        SharedVars.RENDER_TICKS = tickValue;
        SharedVars.PARTIAL_TICKS = partialTicks;
        SharedVars.LIGHT = light;
        if (entity.isInvisibleTo((PlayerEntity)MinecraftClient.getInstance().player) || entity.isInvisible()) {
            return 0.0f;
        }
        CosmeticUser user = this.userHandler.getUser(entity.getUuid());
        if (user == null) {
            return 0.0f;
        }
        user.resetNametagHeight();
        float height = 0.0f;
        if (ModConfig.getConfig().cosmetics && user.hasCosmetics()) {
            try {
                for (RenderCallback callback : this.callbacks) {
                    if (callback.onPreRender(user)) continue;
                    return 0.0f;
                }
                double motionX = entity.prevCapeX + (entity.capeX - entity.prevCapeX) * (double)partialTicks - (entity.prevX + (entity.getX() - entity.prevX) * (double)partialTicks);
                double motionY = entity.prevCapeY + (entity.capeY - entity.prevCapeY) * (double)partialTicks - (entity.prevY + (entity.getY() - entity.prevY) * (double)partialTicks);
                double motionZ = entity.prevCapeZ + (entity.capeZ - entity.prevCapeZ) * (double)partialTicks - (entity.prevZ + (entity.getZ() - entity.prevZ) * (double)partialTicks);
                float motionYaw = entity.prevBodyYaw + (entity.bodyYaw - entity.prevBodyYaw) * partialTicks;
                double yawSin = MathUtils.sin((float)(motionYaw * (float)Math.PI / 180.0f));
                double yawCos = -MathUtils.cos((float)(motionYaw * (float)Math.PI / 180.0f));
                float rotation = (float)motionY * 10.0f;
                rotation = MathUtils.clampFloat((float)rotation, (float)-6.0f, (float)32.0f);
                float motion = (float)(motionX * yawSin + motionZ * yawCos) * 100.0f;
                float motionFront = MathUtils.clampFloat((float)motion, (float)0.0f, (float)180.0f);
                float motionBack = MathUtils.clampFloat((float)motion, (float)-180.0f, (float)0.0f);
                stack.push();
                for (java.util.Map.Entry<Integer, ModelData> entry : user.getCosmetics().entrySet()) {
                    int armorSlot;
                    CosmeticModel model;
                    ModelData data = entry.getValue();
                    Integer id = entry.getKey();
                    if (data == null || !data.isActive() || (model = (CosmeticModel)this.loader.getCosmetics().get(id)) == null || ModConfig.getConfig().armorMode && (armorSlot = model.getPosition().getArmorSlot()) != -1 && !entity.getInventory().getArmorStack(armorSlot).isEmpty() && (model.getCategory() != ModelCategory.SHIELD.getId() || !ModConfig.getConfig().replaceShield || !entity.getEquippedStack(EquipmentSlot.OFFHAND).isOf(Items.SHIELD)) || model.getCategory() == ModelCategory.SHIELD.getId() && ModConfig.getConfig().replaceShield != entity.getEquippedStack(EquipmentSlot.OFFHAND).isOf(Items.SHIELD)) continue;
                    float modelHeight = model.getHeight();
                    if (model.getPosition() == ModelPosition.HEAD || model.getPosition() == ModelPosition.ABOVE_HEAD) {
                        modelHeight += data.y;
                    }
                    if (height < modelHeight) {
                        height = modelHeight;
                    }
                    this.renderCosmetic(stack, provider, light, entity, model, data, movementFactor, walkingSpeed, tickValue, motionFront, motionBack, rotation, modelPlayer, slim, true);
                }
                stack.pop();
                user.setNametagHeight(height);
                for (RenderCallback callback : this.callbacks) {
                    callback.onPostRender(user);
                }
            }
            catch (Throwable e) {
                MoreCosmetics.catchThrowable((Throwable)e);
            }
        }
        return height;
    }

    public void renderCosmeticFirstPerson(MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, int light, AbstractClientPlayerEntity entity, CosmeticModel model, ModelData data, boolean firstPerson, boolean leftHanded) {
        STACK.rotateZ(180.0f);
        if (firstPerson) {
            STACK.scale(0.9f, 0.9f, 0.9f);
            STACK.translate(0.0f, 0.4f, -0.1f);
            if (MoreCosmetics.getInstance().getVersionAdapter().isMouseButtonDown(1)) {
                STACK.rotateZ(leftHanded ? -25.0f : 25.0f);
                STACK.translate(-0.15f, -0.15f, 0.0f);
            }
            this.renderCosmetic(stack, vertexConsumerProvider, light, entity, model, data, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, true, false);
            return;
        }
        if (leftHanded) {
            return;
        }
        STACK.rotateY(180.0f);
        float[] t = model.getSideTranform() != null ? model.getSideTranform() : ModelLoader.SIDE_TRANSFORM;
        STACK.translate(t[0], t[1], t[2]);
        this.renderCosmetic(stack, vertexConsumerProvider, light, entity, model, data, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, entity.getSkinTextures().model() == SkinTextures.Model.SLIM, true);
    }

    public void renderCosmetic(MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, int light, AbstractClientPlayerEntity entity, CosmeticModel model, ModelData data, float movementFactor, float walkingSpeed, float tickValue, float motionFront, float motionBack, float rotation, PlayerEntityModel modelPlayer, boolean slim, boolean transformModel) {
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        STACK.push();
        float y = this.transformToEntity((PositionModel)model, modelPlayer, false, slim, STACK);
        STACK.rotateZ(180.0f);
        if (transformModel) {
            this.transformModel((PositionModel)model, y, (SettingOverlay)data, STACK);
        }
        if (data.resize) {
            STACK.scale(model.getResizeVal(), model.getResizeVal(), model.getResizeVal());
        }
        float[] adj = model.getAdjustment();
        if (adj != null && adj.length > 5) {
            float armorAdjust = 0.0f;
            if (model.getCategory() == ModelCategory.SHIELD.getId() && entity != null && !entity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                armorAdjust = 0.05f;
            }
            STACK.scale(adj[0], adj[1], adj[2] + armorAdjust);
            STACK.translate(adj[3], adj[4], adj[5] - armorAdjust);
        }
        this.appendAnimations((AnimationModel)model, tickValue, motionFront, motionBack, rotation, (SettingOverlay)data, STACK);
        if (model.hasTextureModels()) {
            this.renderTextureModels((List)model.getTextureModels(), data, modelPlayer, slim, tickValue, motionFront, motionBack, rotation, vertexConsumerProvider, light, model.getName(), true);
        }
        if (model.hasItemModels() && entity != null) {
            this.renderItemModels(entity, model, data, modelPlayer, tickValue, motionFront, motionBack, rotation, light);
        }
        String texName = model.getTextureName();
        net.minecraft.client.render.VertexConsumer consumer = null;
        if (texName != null) {
            CustomImage customImage;
            Identifier loc = (Identifier)RESLOCS.get(texName);
            if (loc == null) {
                loc = Identifier.of((String)("morecosmetics/" + texName));
                RESLOCS.put(texName, loc);
            }
            if (data.url != null && !data.url.isEmpty() && (customImage = this.ctm.getImage(data.url + model.getTextureName(), data.url, null)) != null) {
                loc = customImage.getLocation();
            }
            MinecraftClient.getInstance().getTextureManager().bindTexture(loc);
            RenderSystem.setShaderTexture((int)0, (Identifier)loc);
            if (vertexConsumerProvider != null) {
                consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent((Identifier)loc));
            } else {
                RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapProgram);
            }
        } else {
            consumer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
        }
        STACK.push();
        if (model.hasAnimatedModel() && entity != null) {
            if (model.getAnimatedModel().hasAnimations()) {
                AnimationEventType current = AnimationEventType.IDLE;
                if (entity.getX() - entity.prevX != 0.0 || entity.getZ() - entity.prevZ != 0.0) {
                    current = AnimationEventType.MOVING;
                }
                if (entity.isSneaking()) {
                    current = AnimationEventType.SNEAKING;
                }
                model.getAnimatedModel().setAnimationEvent(current);
            }
            this.geoModelRenderer.updateAnimation(model.getId(), model.getAnimatedModel(), SharedVars.PARTIAL_TICKS);
        }
        int overlay = entity != null && transformModel && ModConfig.getConfig().damageTint ? LivingEntityRenderer.getOverlay((LivingEntity)entity, (float)0.0f) : OverlayTexture.DEFAULT_UV;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        this.geoModelRenderer.renderModel(model, vertexConsumerProvider, (VertexConsumer)consumer, this, model.getSubModels(), data, modelPlayer, slim, overlay, light, tickValue, motionFront, motionBack, rotation);
        RenderSystem.disableBlend();
        STACK.pop();
        STACK.pop();
    }

    private void renderItemModels(AbstractClientPlayerEntity entity, CosmeticModel model, ModelData data, PlayerEntityModel modelPlayer, float tickValue, float motionFront, float motionBack, float rotation, int light) {
        for (int i = 0; i < model.getItemModels().size(); ++i) {
            int id;
            ItemStack itemStack;
            SettingOverlay subData;
            ItemModel itemModel = (ItemModel)model.getItemModels().get(i);
            boolean useData = data.item != null && data.item.length > i;
            SettingOverlay settingOverlay = subData = useData ? data.item[i] : PLACEHOLDER_DATA;
            if (!subData.visible || (itemStack = (ItemStack)ITEMS.get(id = subData.id == 0 ? itemModel.getItemId() : subData.id)) == null) continue;
            STACK.push();
            boolean slim = entity.getSkinTextures().model() == SkinTextures.Model.SLIM;
            float y = this.transformToEntity((PositionModel)itemModel, modelPlayer, true, slim, STACK);
            this.transformModel((PositionModel)itemModel, y, subData, STACK);
            this.appendAnimations((AnimationModel)itemModel, tickValue, motionFront, motionBack, rotation, subData, STACK);
            STACK.scale(0.5f, 0.5f, 0.5f);
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.getItemRenderer().renderItem(itemStack, ModelTransformationMode.FIXED, false, (MatrixStack)STACK.get(), (VertexConsumerProvider)mc.getBufferBuilders().getEntityVertexConsumers(), light, 1, null);
            STACK.pop();
        }
    }

    protected void renderTextureModels(List<TextureModel> models, ModelData data, PlayerEntityModel modelPlayer, boolean slim, float tickValue, float motionFront, float motionBack, float rotation, VertexConsumerProvider provider, int light, String name, boolean excludeBounded) {
        for (TextureModel textureModel : models) {
            String key;
            CustomImage loc;
            String url;
            SettingOverlay subData;
            if (excludeBounded == textureModel.isBounded()) continue;
            boolean useData = data.texture != null && data.texture.length > textureModel.getId();
            SettingOverlay settingOverlay = subData = useData ? data.texture[textureModel.getId()] : PLACEHOLDER_DATA;
            if (!subData.visible || (url = subData.url == null ? textureModel.getUrl() : subData.url) == null || url.length() < 3 || (loc = this.ctm.getImage(key = url + name + textureModel.getId(), url, textureModel.getMask())) == null) continue;
            STACK.push();
            float y = this.transformToEntity((PositionModel)textureModel, modelPlayer, true, slim, STACK);
            this.transformModel((PositionModel)textureModel, y, subData, STACK);
            this.appendAnimations((AnimationModel)textureModel, tickValue, motionFront, motionBack, rotation, subData, STACK);
            MinecraftClient.getInstance().getTextureManager().bindTexture(loc.getLocation());
            STACK.scale(-0.5f, -0.5f, -0.5f);
            int color = subData.color == 0 ? textureModel.getColor() : subData.color;
            DrawUtils.drawTextureWithLight((float)-0.5f, (float)-0.5f, (float)256.0f, (float)256.0f, (float)loc.getFactor(), (float)1.0f, (int)color, (int)light, (Identifier)loc.getLocation(), (VertexConsumerProvider)provider);
            STACK.pop();
        }
    }

    protected void transformModel(PositionModel model, float y, SettingOverlay data, RenderStack<MatrixStack> stack) {
        stack.translate(model.getX() + data.x, model.getY() + y + data.y, model.getZ() + data.z);
        stack.rotateY(model.getYaw() + data.yaw);
        stack.rotateX(model.getPitch() + data.pitch);
        stack.rotateZ(model.getRoll() + data.roll);
        stack.scale(model.getScale() * data.scale, model.getScale() * data.scale, model.getScale() * data.scale);
    }

    protected void appendAnimations(AnimationModel model, float tickValue, float motionFront, float motionBack, float rotation, SettingOverlay data, RenderStack<MatrixStack> stack) {
        if (!model.hasAnimations() || !data.animation) {
            return;
        }
        for (AnimationType type : model.getAnimations().keySet()) {
            Animation animation = (Animation)model.getAnimations().get(type);
            AnimationAxis a = animation.getAxis();
            float m = animation.getMultiplier() * data.mulitply;
            switch (type) {
                case BOUNCE: {
                    float bounce = m * MathUtils.cos((float)(tickValue / 10.0f)) / 20.0f;
                    stack.translate((float)a.x * bounce, (float)a.y * bounce, (float)a.z * bounce);
                    break;
                }
                case ROTATE: {
                    float rotate = m * tickValue;
                    stack.rotate((float)a.x * rotate, (float)a.y * rotate, (float)a.z * rotate);
                    break;
                }
                case FORWARD: {
                    float forward = m * (motionFront / 3.0f);
                    stack.rotate((float)a.x * forward, (float)a.y * forward, (float)a.z * forward);
                    break;
                }
                case BACKWARD: {
                    float backward = m * (motionBack / 3.0f);
                    stack.rotate((float)a.x * backward, (float)a.y * backward, (float)a.z * backward);
                    break;
                }
                case RESIZE: {
                    float resize = 1.0f + m * MathUtils.cos((float)(tickValue / 10.0f)) / 20.0f;
                    stack.scale(resize, resize, resize);
                    break;
                }
                case MOTION: {
                    break;
                }
            }
        }
    }

    protected float transformToEntity(PositionModel model, PlayerEntityModel modelPlayer, boolean flip, boolean slim, RenderStack stack) {
        float y = 0.0f;
        if (modelPlayer != null) {
            switch (model.getPosition()) {
                case FREE: {
                    break;
                }
                case HEAD: {
                    this.transformToModel(modelPlayer.head, flip, false, stack);
                    y = -1.5f;
                    break;
                }
                case BODY: {
                    this.transformToModel(modelPlayer.body, flip, false, stack);
                    y = -0.75f;
                    break;
                }
                case ABOVE_HEAD: {
                    this.transformToModel(modelPlayer.head, flip, false, stack);
                    y = -1.1f;
                    break;
                }
                case LEFT_ARM: {
                    this.transformToModel(modelPlayer.leftArm, flip, false, stack);
                    if (slim) {
                        stack.translate(-0.03125f, 0.0f, 0.0f);
                    }
                    y = -0.25f;
                    break;
                }
                case RIGHT_ARM: {
                    this.transformToModel(modelPlayer.rightArm, flip, true, stack);
                    if (slim) {
                        stack.translate(0.03125f, 0.0f, 0.0f);
                    }
                    y = -0.25f;
                    break;
                }
                case RIGHT_LEG: {
                    this.transformToModel(modelPlayer.rightLeg, flip, false, stack);
                    y = -0.35f;
                    break;
                }
                case LEFT_LEG: {
                    this.transformToModel(modelPlayer.leftLeg, flip, false, stack);
                    y = -0.35f;
                    break;
                }
            }
        }
        return y;
    }

    private void transformToModel(ModelPart model, boolean flip, boolean isRightArm, RenderStack stack) {
        if (flip) {
            stack.rotateZ(180.0f);
        }
        float pX = model.pivotX * 0.0625f;
        float pY = model.pivotY * 0.0625f;
        float pZ = model.pivotZ * 0.0625f;
        
        if (isRightArm) {
            stack.translate(-pX, pY, pZ);
            stack.rotate(model.pitch * 57.295776f, -model.yaw * 57.295776f, -model.roll * 57.295776f);
        } else {
            stack.translate(pX, pY, pZ);
            stack.rotate(model.pitch * 57.295776f, model.yaw * 57.295776f, model.roll * 57.295776f);
        }

        if (flip) {
            stack.rotateZ(180.0f);
        }
    }
}

